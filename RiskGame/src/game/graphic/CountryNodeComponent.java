package game.graphic;

import game.core.Country;
import game.core.Utils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JComponent;
import javax.swing.JLabel;

/**
 * @author MiFans (Sukrat Kashyap - 14200092, Zhesi Ning - 12252511)
 * @Description this class represents a node on the risk map for each country
 */
public class CountryNodeComponent extends JComponent implements IRefreshable {

    public static final int COUNTRY_RADIUS = 12;
    public static final int COUNTRY_DIAMETER = COUNTRY_RADIUS * 2;

    public static final int PLAYER_RADIUS = 6;
    public static final int PLAYER_DIAMETER = PLAYER_RADIUS * 2;

    public static final int LABEL_MARGIN_X = 10;
    public static final int LABEL_MARGIN_Y = 3;

    private final Country _country;
    private final int nameOffsetX = 10;
    private final int nameOffsetY = 25;
    private JLabel _label;

    /**
     *
     * @param country country for which the node should be created
     */
    public CountryNodeComponent(Country country) {
        super();
        _country = country;
        this.setLayout(null);
        this.setBounds(1, 1, 1, 1);
//        this.setBorder(BorderFactory.createLineBorder(Color.BLACK));

    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        _label = new JLabel(_country.getName(), JLabel.CENTER);
        int fontWidth = g2.getFontMetrics(_label.getFont()).stringWidth(_label.getText());
        int fontHeight = g2.getFontMetrics(_label.getFont()).getHeight();
        _label.setBounds(0, 0, COUNTRY_DIAMETER + fontWidth + LABEL_MARGIN_X, fontHeight + LABEL_MARGIN_Y);
//        _label.setBorder(BorderFactory.createLineBorder(Color.BLACK));

        this.setBounds(_country.getXCoOrdinate() - fontWidth / 2 - COUNTRY_RADIUS - LABEL_MARGIN_X,
                _country.getYCoOrdinate() - fontHeight - COUNTRY_RADIUS - LABEL_MARGIN_Y,
                COUNTRY_DIAMETER + fontWidth + LABEL_MARGIN_X, COUNTRY_DIAMETER + fontHeight + LABEL_MARGIN_Y);

        //drawing circle
        int circleCentreX = fontWidth / 2 + LABEL_MARGIN_X + COUNTRY_RADIUS;
        int circleCentreY = fontHeight + LABEL_MARGIN_Y + COUNTRY_RADIUS;
        g2.setColor(_country.getContinent().getColor());
        Ellipse2D.Double countryCircle = new Ellipse2D.Double(circleCentreX - COUNTRY_RADIUS,
                circleCentreY - COUNTRY_RADIUS,
                COUNTRY_DIAMETER, COUNTRY_DIAMETER);
        g2.fill(countryCircle);

        //drawing smaller circle
        if (_country.getOwnerOfTheCountry() != null) {
            g2.setColor(_country.getOwnerOfTheCountry().getColor());
            Ellipse2D.Double playerCircle = new Ellipse2D.Double(circleCentreX - PLAYER_RADIUS,
                    circleCentreY - PLAYER_RADIUS,
                    PLAYER_DIAMETER, PLAYER_DIAMETER);
            g2.fill(playerCircle);
        }
        this.add(_label);
        this.SetTheToolTip();
    }

    /**
     * Sets the tooltip for the country node.
     */
    public void SetTheToolTip() {
        StringBuilder builder = new StringBuilder();
        builder.append("<html><body>");
        builder.append("Name: ").append(_country.getName());
        if (_country.getOwnerOfTheCountry() != null) {
            builder.append("<br>");
            builder.append("Player: ").append(_country.getOwnerOfTheCountry().getName());
            builder.append("<br>");
            builder.append("Armies: ").append(_country.getArmyInCountry());
            builder.append("Enter shortcut-  ").append(_country.getAbbreviations()).append("  -for this country.");
        }
        builder.append("</body></html>");
        this.setToolTipText(builder.toString());
    }

    @Override
    public void refresh() {
        this.repaint();
        this.revalidate();
    }
}
