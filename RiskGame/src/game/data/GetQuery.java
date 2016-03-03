package game.data;

import game.core.Card;
import game.core.CardType;
import game.core.Continent;
import game.core.ContinentIndex;
import game.core.Country;
import game.core.CountryIndex;
import game.core.DataFactory;
import game.core.Player;
import game.core.PlayerType;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * @author Sukrat Kashyap (14200092)
 * @Description An abstraction on the country and continent map. this class
 * gives various methods to search country or continent
 */
public class GetQuery {

    private final DataFactory _dataFactory;

    public GetQuery() {
        _dataFactory = DataFactory.getInstance();
    }

    /**
     *
     * @return List of all the countries
     */
    public List<Country> getCountryList() {
        List<Country> countryList = new ArrayList<>();
        _dataFactory.getCountryMap().entrySet().stream()
                .forEach((countryMapElem) -> {
                    countryList.add(countryMapElem.getValue());
                });
        return Collections.unmodifiableList(countryList);
    }

    /**
     *
     * @param countryIdList List of country ids
     * @return gives list of countries bases on the country ids passd
     */
    public List<Country> getCountryList(List<CountryIndex> countryIdList) {
        Map<CountryIndex, Country> countryMap = _dataFactory.getCountryMap();
        List<Country> countryList = new ArrayList<>();
        countryIdList.stream()
                .filter((countryId) -> (countryMap.containsKey(countryId)))
                .forEach((countryId) -> {
                    countryList.add(countryMap.get(countryId));
                });
        return Collections.unmodifiableList(countryList);
    }

    /**
     *
     * @param countryId id of the country
     * @return the country object corresponding to countryId
     */
    public Country getCountry(CountryIndex countryId) {
        Map<CountryIndex, Country> countryMap = _dataFactory.getCountryMap();
        if (countryMap.containsKey(countryId)) {
            return countryMap.get(countryId);
        } else {
            return null;
        }
    }

    /**
     *
     * @param continentId continent id to search
     * @return Continent which has the continent index
     */
    public Continent getContinent(ContinentIndex continentId) {
        Map<ContinentIndex, Continent> continentMap = _dataFactory.getContinentMap();
        if (continentMap.containsKey(continentId)) {
            return continentMap.get(continentId);
        } else {
            return null;
        }
    }

    /**
     *
     * @return list of players
     */
    public List<Player> getPlayerList() {
        List<Player> playerList = new ArrayList<>();
        _dataFactory.getPlayerMap().entrySet()
                .stream()
                .forEach((player) -> {
                    playerList.add(player.getValue());
                });
        return Collections.unmodifiableList(playerList);
    }

    public List<Player> getPlayerList(PlayerType playerType) {
        List<Player> playerList = new ArrayList<>();
        _dataFactory.getPlayerMap().entrySet()
                .stream()
                .filter((player) -> player.getValue().getPlayerType() == playerType)
                .forEach((player) -> {
                    playerList.add(player.getValue());
                });
        return Collections.unmodifiableList(playerList);
    }

    /**
     *
     * @param playerName name of the player
     * @return the player whose name has been passed
     */
    public Player getPlayer(String playerName) {
        Map<String, Player> playerMap = _dataFactory.getPlayerMap();
        if (playerMap.containsKey(playerName)) {
            return playerMap.get(playerName);
        } else {
            return null;
        }
    }

    public List<Player> getOrderedMainPlayerList() {
        List<Player> playerList = new ArrayList<>();
        _dataFactory.getPlayerMap().entrySet()
                .stream()
                .filter((player) -> player.getValue().getPlayerType() == PlayerType.MainPlayer)
                .sorted((p1, p2) -> Integer.compare(p1.getValue().getOrder(), p2.getValue().getOrder()))
                .forEach((player) -> {
                    playerList.add(player.getValue());
                });
        return Collections.unmodifiableList(playerList);
    }

    public List<String> getCountryNameListByPlayerName(String playerName) {
        List<String> countryNameList = new ArrayList<>();
        _dataFactory.getCountryMap().entrySet()
                .stream()
                .filter((c) -> c.getValue().getOwnerOfTheCountry().getName().equals(playerName))
                .forEach((c) -> {
                    countryNameList.add(c.getValue().getName());
                });
        return Collections.unmodifiableList(countryNameList);
    }

}
