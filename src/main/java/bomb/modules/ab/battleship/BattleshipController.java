package bomb.modules.ab.battleship;

import bomb.abstractions.Resettable;
import bomb.modules.ab.battleship.extra.ExtraBoardController;
import bomb.tools.pattern.facade.FacadeFX;
import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import static bomb.modules.ab.battleship.BattleshipUITranslator.translateToFrontendGrid;
import static bomb.tools.pattern.facade.FacadeFX.disable;
import static bomb.tools.pattern.facade.FacadeFX.enable;
import static bomb.tools.pattern.factory.TextFormatterFactory.createBattleshipCounterTextFormatter;
import static javafx.scene.control.Alert.AlertType.ERROR;
import static javafx.scene.control.Alert.AlertType.INFORMATION;

public final class BattleshipController implements Resettable {
    @FXML
    private MFXButton confirmationButton, solveButton;

    @FXML
    private MFXTextField rowOne, rowTwo, rowThree, rowFour, rowFive,
            columnOne, columnTwo, columnThree, columnFour, columnFive;

    @FXML
    private MFXTextField battleshipTextField, cruiserTextField,
            destroyerTextField, submarineTextField;

    @FXML
    private Rectangle oneOne, oneTwo, oneThree, oneFour, oneFive,
            twoOne, twoTwo, twoThree, twoFour, twoFive,
            threeOne, threeTwo, threeThree, threeFour, threeFive,
            fourOne, fourTwo, fourThree, fourFour, fourFive,
            fiveOne, fiveTwo, fiveThree, fiveFour, fiveFive;

    private final Rectangle[][] frontendGrid;
    private final MFXTextField[] rowGroup, columnGroup, shipCountFields;

    private List<Rectangle> radarRectangles;

    public BattleshipController() {
        frontendGrid = new Rectangle[Ocean.BOARD_LENGTH][];
        rowGroup = new MFXTextField[Ocean.BOARD_LENGTH];
        columnGroup = new MFXTextField[Ocean.BOARD_LENGTH];
        shipCountFields = new MFXTextField[Ship.SHIPS.length];
    }

    public void initialize() {
        frontendGrid[0] = new Rectangle[]{oneOne, oneTwo, oneThree, oneFour, oneFive};
        frontendGrid[1] = new Rectangle[]{twoOne, twoTwo, twoThree, twoFour, twoFive};
        frontendGrid[2] = new Rectangle[]{threeOne, threeTwo, threeThree, threeFour, threeFive};
        frontendGrid[3] = new Rectangle[]{fourOne, fourTwo, fourThree, fourFour, fourFive};
        frontendGrid[4] = new Rectangle[]{fiveOne, fiveTwo, fiveThree, fiveFour, fiveFive};
        setRectanglesToUnknown(frontendGrid);

        setupTextFieldGroup(rowGroup, rowOne, rowTwo, rowThree, rowFour, rowFive);
        setupTextFieldGroup(columnGroup, columnOne, columnTwo, columnThree, columnFour, columnFive);

        setupTextFieldGroup(shipCountFields, battleshipTextField, cruiserTextField,
                destroyerTextField, submarineTextField);
    }

    private static void setRectanglesToUnknown(Rectangle[][] frontendGrid) {
        Color unknownColor = Tile.UNKNOWN.getTileColor();
        Arrays.stream(frontendGrid)
                .flatMap(Arrays::stream)
                .forEach(rectangle -> rectangle.setFill(unknownColor));
    }

    private static void setupTextFieldGroup(MFXTextField[] group, MFXTextField... fields) {
        int counter = 0;
        for (MFXTextField field : fields) {
            field.setTextFormatter(createBattleshipCounterTextFormatter());
            group[counter++] = field;
        }
    }

    @FXML
    private void enableSolveButton() {
        Stream<MFXTextField> stream = Stream.concat(Arrays.stream(rowGroup), Arrays.stream(columnGroup));
        stream = Stream.concat(stream, Arrays.stream(shipCountFields));

        boolean anyEmptyFields = stream.map(TextField::getText)
                .anyMatch(String::isEmpty);

        if (anyEmptyFields || radarRectangles == null) {
            disable(solveButton);
            return;
        }

        List<Rectangle> rectangles = radarRectangles.stream()
                .filter(rectangle -> !rectangle.isDisabled())
                .toList();

        if (rectangles.isEmpty()) {
            disable(solveButton);
            return;
        }
        solveButton.setDisable(rectangles.stream()
                        .anyMatch(BattleshipController::isRectangleUnknownOrRadar)
        );
    }

    @FXML
    private void cycleColors(MouseEvent event) {
        Rectangle source = (Rectangle) event.getSource();
        Color currentColor = (Color) source.getFill();

        if (Tile.CLEAR.getTileColor().equals(currentColor)) {
            source.setFill(Tile.SHIP.getTileColor());
        } else if (Tile.SHIP.getTileColor().equals(currentColor)) {
            source.setFill(Tile.UNKNOWN.getTileColor());
        } else if (Tile.UNKNOWN.getTileColor().equals(currentColor) ||
                Tile.RADAR.getTileColor().equals(currentColor)) {
            source.setFill(Tile.CLEAR.getTileColor());
        }

        if (radarRectangles != null && radarRectangles.contains(source)) {
            enableConfirmationButton();
            enableSolveButton();
        }
    }

    private void enableConfirmationButton() {
        confirmationButton.setDisable(
            radarRectangles.stream().anyMatch(BattleshipController::isRectangleUnknownOrRadar)
        );
    }

    @FXML
    private void revealRadarSpots() {
        Battleship.wipeRadarSpots();
        setAllRectanglesUnknown(frontendGrid);

        Set<String> radarLocations;
        try {
            radarLocations = Battleship.calculateRadarPositions();
        } catch (IllegalArgumentException e) {
            FacadeFX.setAlert(ERROR, e.getMessage(),
                    "Serial Code error", "Incomplete Edgework");
            return;
        }

        radarRectangles = revealSpotsOnFrontend(radarLocations);
        String output = String.join(", ", radarLocations)
                .toUpperCase();

        disable(confirmationButton);
        FacadeFX.setAlert(INFORMATION, output,
                "Current Radar Locations", "Bomb Info");
    }

    private List<Rectangle> revealSpotsOnFrontend(Set<String> radarLocations) {
        return radarLocations.stream()
                .map(location -> new int[]{
                        location.charAt(1) - '1',
                        location.charAt(0) - 'a'
                })
                .map(location -> frontendGrid[location[0]][location[1]])
                .peek(BattleshipController::updateRadarSpot)
                .toList();
    }

    private static void updateRadarSpot(Rectangle rectangle) {
        rectangle.setFill(Tile.RADAR.getTileColor());
        enable(rectangle);
    }

    @FXML
    private void confirmRadarSpots() {
        Tile[] confirmedSpots = radarRectangles.stream()
                .filter(rectangle -> !isRectangleUnknownOrRadar(rectangle))
                .map(Rectangle::getFill)
                .map(fill -> (Color) fill)
                .map(BattleshipController::determineWaterOrShipTile)
                .toArray(Tile[]::new);

        try {
            Battleship.confirmRadarSpots(confirmedSpots);
            FacadeFX.setAlert(INFORMATION, "Radar Spots have been logged");
            disable(confirmationButton);
        } catch (IllegalArgumentException e) {
            FacadeFX.setAlert(ERROR, e.getMessage(),
                    "", "Battleship State Error");
        }
    }

    @FXML
    private void solveBoard() {
        int[] rowCounters = getNumbersFromFields(rowGroup);
        int[] columnCounters = getNumbersFromFields(columnGroup);
        int[] shipCounts = getNumbersFromFields(shipCountFields);

        Battleship.setRowCounters(rowCounters);
        Battleship.setColumnCounters(columnCounters);

        Ship[] ships = Ship.SHIPS;
        for (int i = 0; i < shipCounts.length; i++) {
            ships[i].setCurrentQuantity(shipCounts[i]);
        }

        Set<Ocean> solve;
        try {
            solve = Battleship.solveOcean();
        } catch (IllegalArgumentException e) {
            FacadeFX.setAlert(ERROR, e.getMessage(),
                    "Incomplete Information Given", "Information Error");
            return;
        }

        if (solve.size() == 1) {
            translateToFrontendGrid(frontendGrid, solve.iterator().next());
        } else {
            FacadeFX.setAlert(ERROR, "Displaying extra solutions in new window(s)",
                    "Multiple solutions detected", "Unexpected state");
            List<Ocean> solutions = new ArrayList<>(solve);

            translateToFrontendGrid(frontendGrid, solutions.get(0));

            solutions.subList(1, solutions.size())
                    .stream()
                    .map(ExtraBoardController::new)
                    .forEach(ExtraBoardController::show);
        }
    }

    private static int[] getNumbersFromFields(TextField[] fields) {
        return Arrays.stream(fields)
                .map(TextField::getText)
                .mapToInt(Integer::parseInt)
                .toArray();
    }

    @FXML
    private void resetPuzzle() {
        reset();
    }

    public void reset() {
        Battleship.reset();
        solveButton.setDisable(true);
        confirmationButton.setDisable(true);

        radarRectangles = null;

        FacadeFX.clearMultipleTextFields(rowGroup);
        FacadeFX.clearMultipleTextFields(columnGroup);
        FacadeFX.clearMultipleTextFields(shipCountFields);

        setAllRectanglesUnknown(frontendGrid);
    }

    private static boolean isRectangleUnknownOrRadar(Rectangle rectangle) {
        return rectangle.getFill().equals(Tile.UNKNOWN.getTileColor()) ||
                rectangle.getFill().equals(Tile.RADAR.getTileColor());
    }

    private static Tile determineWaterOrShipTile(Color color) {
        return Tile.CLEAR.getTileColor().equals(color) ?
                Tile.CLEAR :
                Tile.SHIP;
    }

    private static void setAllRectanglesUnknown(Rectangle[][] frontendGrid) {
        Arrays.stream(frontendGrid)
                .flatMap(Arrays::stream)
                .forEach(rectangle -> rectangle.setFill(Tile.UNKNOWN.getTileColor()));
    }
}
