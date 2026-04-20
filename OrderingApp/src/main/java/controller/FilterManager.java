package controller;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import model.enums.DealFilter;
import model.enums.PriceCategory;
import model.enums.SortType;
import model.fruitinfo.Fruit;
import model.fruitinfo.LocatieFruit;
import model.vestigingen.ProvincieVestigingen;
import model.vestigingen.Vestiging;
import view.FruitCard;

public class FilterManager {
    private final ObservableList<FruitCard> masterList;
    private final SortedList<FruitCard> sortedList;
    private final FilteredList<FruitCard> filteredList;

    private final Map<SortType, Comparator<FruitCard>> sorters = new HashMap<>();
    private final Map<String, Predicate<FruitCard>> activeFilters = new HashMap<>();

    private final List<ProvincieVestigingen> provincies;
    private List<Vestiging> alleVestigingen = new ArrayList<>();
    private Vestiging activeVestiging;

    private static final String KEY_PRICE = "price";
    private static final String KEY_AVAILABILITY = "availability";
    private static final String KEY_DEAL = "deal";
    private static final String KEY_SEARCH = "search";
    private static final String KEY_FRUIT_OF_THE_WEEK = "fruitOfTheWeek";

    private String searchQuery = "";

    public FilterManager(ObservableList<FruitCard> masterList, List<ProvincieVestigingen> provincies) {
        this.masterList = masterList;
        this.filteredList = new FilteredList<>(masterList, f -> true);
        this.sortedList = new SortedList<>(filteredList);

        this.provincies = provincies;
        setAlleVestigingen(provincies);

        initSorters();
        applySort(SortType.A_Z);
    }

    public SortedList<FruitCard> getSortedList() {
        return sortedList;
    }

    /* --------------------------- Sorting --------------------------- */

    private void initSorters() {
        sorters.put(SortType.A_Z,
                Comparator.comparing(fc -> fc.getFruit().getFruitName()));

        sorters.put(SortType.Z_A,
                Comparator.comparing((FruitCard fc) -> fc.getFruit().getFruitName()).reversed());

        sorters.put(SortType.PRICE_LOW_HIGH,
                Comparator.comparing(fc -> fc.getFruit().getPrice()));

        sorters.put(SortType.PRICE_HIGH_LOW,
                Comparator.comparing((FruitCard fc) -> fc.getFruit().getPrice()).reversed());
    }

    public void applySort(SortType type) {
        Comparator<FruitCard> comp = sorters.get(type);
        if (comp != null)
            sortedList.setComparator(comp);
    }

    /* --------------------------- Filtering Base --------------------------- */

    private void setFilter(String key, Predicate<FruitCard> predicate) {
        if (predicate == null) {
            activeFilters.remove(key);
            System.out.println("check");
        } else
            activeFilters.put(key, predicate);
        reapplyFilters();
    }

    private void clearFilter(String key) {
        activeFilters.remove(key);
        reapplyFilters();
    }

    public void clearAllFilters() {
        activeFilters.clear();
        reapplyFilters();
    }

    private void reapplyFilters() {
        if (activeFilters.isEmpty()) {
            filteredList.setPredicate(f -> true);
            return;
        }

        Predicate<FruitCard> combined = activeFilters.values()
                .stream()
                .reduce(f -> true, Predicate::and);

        filteredList.setPredicate(combined);
    }

    /* --------------------------- Price Filter --------------------------- */

    public void setPriceCategory(PriceCategory category) {
        switch (category) {
            case CHEAP ->
                setFilter(KEY_PRICE,
                        fc -> fc.getFruit().getDisplayPrice() <= 2.5);

            case EXPENSIVE ->
                setFilter(KEY_PRICE,
                        fc -> fc.getFruit().getDisplayPrice() > 2.5);

            case ALL ->
                clearFilter(KEY_PRICE);
        }
    }

    /* --------------------------- Deal Filter --------------------------- */

    public void setDealFilter(DealFilter filter) {
        switch (filter) {
            case DEALS_ONLY ->
                setFilter(KEY_DEAL,
                        fc -> fc.getFruit().hasActiveDeal());

            case NO_DEALS ->
                setFilter(KEY_DEAL,
                        fc -> !fc.getFruit().hasActiveDeal());

            case ALL ->
                clearFilter(KEY_DEAL);
        }
    }

    /* -------------------------- Availability Filter -------------------------- */

    public List<Vestiging> getVestigingen() {
        return alleVestigingen;
    }

    public void setAlleVestigingen(List<ProvincieVestigingen> provincies) {
        alleVestigingen = provincies.stream()
                .flatMap(p -> p.getVestigingen().stream())
                .collect(Collectors.toList());
    }

    public Vestiging getActiveVestiging() {
        return activeVestiging;
    }

    public void setActiveVestigingByName(String name) {
        activeVestiging = alleVestigingen.stream()
                .filter(v -> v.getStad().equalsIgnoreCase(name))
                .findFirst()
                .orElse(null);
    }

    private void updateAvailabilityFilter() {
        if (activeVestiging == null) {
            clearFilter(KEY_AVAILABILITY);
            return;
        }

        Map<String, Integer> availabilityMap = activeVestiging.getFruit().stream()
                .collect(Collectors.toMap(
                        lf -> lf.getNaam().trim().toLowerCase(),
                        LocatieFruit::getBeschikbaarheidLocatie));

        setFilter(KEY_AVAILABILITY, fc -> {
            String fruitName = fc.getFruit().getFruitName();
            if (fruitName == null)
                return false;

            Integer amount = availabilityMap.get(fruitName.trim().toLowerCase());
            return amount != null && amount > 0;
        });
    }

    public void setAvailabilityOnly(boolean onlyAvailable) {
        if (onlyAvailable) {
            updateAvailabilityFilter();
        } else {
            clearFilter(KEY_AVAILABILITY);
        }
    }

    public boolean isAvailable(Fruit fruit) {
        if (activeVestiging == null)
            return true;
        return activeVestiging.getFruit().stream()
                .filter(lf -> lf.getNaam().equalsIgnoreCase(fruit.getFruitName()))
                .anyMatch(lf -> lf.getBeschikbaarheidLocatie() > 0);
    }

    /* --------------------------- Fruit van de week --------------------------- */

    public void setFruitOfTheWeekOnly(boolean onlyFruitOfTheWeek) {
        if (onlyFruitOfTheWeek) {
            // Hardcoded list of fruit names for "fruit van de week"
            List<String> weekFruits = List.of(
                    "banaan",
                    "perzik",
                    "kers");

            setFilter(KEY_FRUIT_OF_THE_WEEK, fc -> {
                String fruitName = fc.getFruit().getFruitName();
                return fruitName != null && weekFruits.contains(fruitName.toLowerCase());
            });
        } else {
            clearFilter(KEY_FRUIT_OF_THE_WEEK);
        }
    }

    /* --------------------------- Search Filter --------------------------- */

    public void setSearchQuery(String query) {
        this.searchQuery = (query == null ? "" : query.toLowerCase().trim());
        if (searchQuery.isEmpty()) {
            clearFilter(KEY_SEARCH);
        } else {
            setFilter(KEY_SEARCH, fc -> fc.getFruit().getFruitName() != null &&
                    fc.getFruit().getFruitName().toLowerCase().contains(searchQuery));
        }
    }
    
}