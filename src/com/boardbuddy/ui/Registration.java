package com.boardbuddy.ui;

import com.boardbuddy.model.User;
import com.boardbuddy.persistence.UserXml;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;


// This program creates a user when the fields are entered and writes and reads them from an XML


// General logic for reference

// Registration Form → User object → XMLParser → users.xml file
//                                                     ↑
// Login Screen --------------------------------reads from here

/**
 * Registration class contains all of the ui code for the display of the registration panel
 */

class Registration extends JFrame implements ActionListener {


    /**
     * Private variables starting at line 29 and ending at line 56 for use in buttons, fields, and dropdowns
     */
    private Container c;
    private JLabel title;

    private JLabel usernameLabel;
    private JTextField tusername;

    private JLabel passwordLabel;
    private JPasswordField tpass;

    private JLabel confirmPasswordLabel;
    private JPasswordField tconfirm;


    // Gender is not saved to the xml
    private JLabel genderLabel;
    private JRadioButton male;
    private JRadioButton female;
    private JRadioButton attackchopper;
    private ButtonGroup gengp;

    private JLabel dobLabel;
    private JComboBox<String> date;
    private JComboBox<String> month;
    private JComboBox<String> year;

    private JButton sub;
    private JButton reset;
    private JLabel res;


    /**
     * List of dates to populate dropdown
     */
    private String[] dates = {
        "1","2","3","4","5","6","7","8","9","10",
        "11","12","13","14","15","16","17","18","19","20",
        "21","22","23","24","25","26","27","28","29","30","31"
    };


    /**
     * List of months to populate dropdown
     */
    private String[] months = {
        "Jan","Feb","Mar","Apr","May","Jun",
        "Jul","Aug","Sep","Oct","Nov","Dec"
    };

    /**
     * List of years to populate dropdown, 1492 when America was found
     */
    private String[] years = {
        "1492","1493","1494","1495","1496","1497","1498","1499","1500",
        "1501","1502","1503","1504","1505","1506","1507","1508","1509","1510",
        "1511","1512","1513","1514","1515","1516","1517","1518","1519","1520",
        "1521","1522","1523","1524","1525","1526","1527","1528","1529","1530",
        "1531","1532","1533","1534","1535","1536","1537","1538","1539","1540",
        "1541","1542","1543","1544","1545","1546","1547","1548","1549","1550",
        "1551","1552","1553","1554","1555","1556","1557","1558","1559","1560",
        "1561","1562","1563","1564","1565","1566","1567","1568","1569","1570",
        "1571","1572","1573","1574","1575","1576","1577","1578","1579","1580",
        "1581","1582","1583","1584","1585","1586","1587","1588","1589","1590",
        "1591","1592","1593","1594","1595","1596","1597","1598","1599","1600",
        "1601","1602","1603","1604","1605","1606","1607","1608","1609","1610",
        "1611","1612","1613","1614","1615","1616","1617","1618","1619","1620",
        "1621","1622","1623","1624","1625","1626","1627","1628","1629","1630",
        "1631","1632","1633","1634","1635","1636","1637","1638","1639","1640",
        "1641","1642","1643","1644","1645","1646","1647","1648","1649","1650",
        "1651","1652","1653","1654","1655","1656","1657","1658","1659","1660",
        "1661","1662","1663","1664","1665","1666","1667","1668","1669","1670",
        "1671","1672","1673","1674","1675","1676","1677","1678","1679","1680",
        "1681","1682","1683","1684","1685","1686","1687","1688","1689","1690",
        "1691","1692","1693","1694","1695","1696","1697","1698","1699","1700",
        "1701","1702","1703","1704","1705","1706","1707","1708","1709","1710",
        "1711","1712","1713","1714","1715","1716","1717","1718","1719","1720",
        "1721","1722","1723","1724","1725","1726","1727","1728","1729","1730",
        "1731","1732","1733","1734","1735","1736","1737","1738","1739","1740",
        "1741","1742","1743","1744","1745","1746","1747","1748","1749","1750",
        "1751","1752","1753","1754","1755","1756","1757","1758","1759","1760",
        "1761","1762","1763","1764","1765","1766","1767","1768","1769","1770",
        "1771","1772","1773","1774","1775","1776","1777","1778","1779","1780",
        "1781","1782","1783","1784","1785","1786","1787","1788","1789","1790",
        "1791","1792","1793","1794","1795","1796","1797","1798","1799","1800",
        "1801","1802","1803","1804","1805","1806","1807","1808","1809","1810",
        "1811","1812","1813","1814","1815","1816","1817","1818","1819","1820",
        "1821","1822","1823","1824","1825","1826","1827","1828","1829","1830",
        "1831","1832","1833","1834","1835","1836","1837","1838","1839","1840",
        "1841","1842","1843","1844","1845","1846","1847","1848","1849","1850",
        "1851","1852","1853","1854","1855","1856","1857","1858","1859","1860",
        "1861","1862","1863","1864","1865","1866","1867","1868","1869","1870",
        "1871","1872","1873","1874","1875","1876","1877","1878","1879","1880",
        "1881","1882","1883","1884","1885","1886","1887","1888","1889","1890",
        "1891","1892","1893","1894","1895","1896","1897","1898","1899","1900",
        "1901","1902","1903","1904","1905","1906","1907","1908","1909","1910",
        "1911","1912","1913","1914","1915","1916","1917","1918","1919","1920",
        "1921","1922","1923","1924","1925","1926","1927","1928","1929","1930",
        "1931","1932","1933","1934","1935","1936","1937","1938","1939","1940",
        "1941","1942","1943","1944","1945","1946","1947","1948","1949","1950",
        "1951","1952","1953","1954","1955","1956","1957","1958","1959","1960",
        "1961","1962","1963","1964","1965","1966","1967","1968","1969","1970",
        "1971","1972","1973","1974","1975","1976","1977","1978","1979","1980",
        "1981","1982","1983","1984","1985","1986","1987","1988","1989","1990",
        "1991","1992","1993","1994","1995","1996","1997","1998","1999","2000",
        "2001","2002","2003","2004","2005","2006","2007","2008","2009","2010",
        "2011","2012","2013","2014","2015","2016","2017","2018","2019","2020",
        "2021","2022","2023","2024","2025","2026"
    };

    // While date of birth and gender are fields in the panel, they do not save to the xml
    //=========================================================================================

    /** Centering constants — tweak LABEL_X to shift everything left/right */
    private static final int LABEL_X = 250;
    private static final int LABEL_W = 160;
    private static final int FIELD_X = 420;
    private static final int FIELD_W = 200;
    private static final int FIELD_H = 25;

    /**
     * Public registration method including the functions for setting the registration panel up
     */
    public Registration() {
        // Window title
        setTitle("Board Buddy - Register");
        setBounds(300, 90, 900, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        c = getContentPane();
        c.setLayout(null);

        // --- Title ---
        title = new JLabel("Registration Form");
        title.setFont(new Font("Arial", Font.PLAIN, 30));
        title.setSize(300, 30);
        title.setLocation(300, 30);
        c.add(title);

        // --- Username ---
        usernameLabel = new JLabel("Username");
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        usernameLabel.setSize(LABEL_W, 20);
        usernameLabel.setLocation(LABEL_X, 100);
        c.add(usernameLabel);

        tusername = new JTextField();
        tusername.setFont(new Font("Arial", Font.PLAIN, 15));
        tusername.setSize(FIELD_W, FIELD_H);
        tusername.setLocation(FIELD_X, 100);
        c.add(tusername);

        // --- Password ---
        passwordLabel = new JLabel("Password");
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        passwordLabel.setSize(LABEL_W, 20);
        passwordLabel.setLocation(LABEL_X, 150);
        c.add(passwordLabel);

        tpass = new JPasswordField();
        tpass.setFont(new Font("Arial", Font.PLAIN, 15));
        tpass.setSize(FIELD_W, FIELD_H);
        tpass.setLocation(FIELD_X, 150);
        c.add(tpass);

        // --- Confirm Password ---
        confirmPasswordLabel = new JLabel("Confirm Password");
        confirmPasswordLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        confirmPasswordLabel.setSize(LABEL_W, 20);
        confirmPasswordLabel.setLocation(LABEL_X, 200);
        c.add(confirmPasswordLabel);

        tconfirm = new JPasswordField();
        tconfirm.setFont(new Font("Arial", Font.PLAIN, 15));
        tconfirm.setSize(FIELD_W, FIELD_H);
        tconfirm.setLocation(FIELD_X, 200);
        c.add(tconfirm);

        // --- Gender ---
        genderLabel = new JLabel("Gender");
        genderLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        genderLabel.setSize(LABEL_W, 20);
        genderLabel.setLocation(LABEL_X, 260);
        c.add(genderLabel);

        male = new JRadioButton("Male");
        male.setFont(new Font("Arial", Font.PLAIN, 15));
        male.setSelected(true);
        male.setSize(75, 20);
        male.setLocation(FIELD_X, 260);
        c.add(male);

        female = new JRadioButton("Female");
        female.setFont(new Font("Arial", Font.PLAIN, 15));
        female.setSize(80, 20);
        female.setLocation(FIELD_X + 80, 260);
        c.add(female);

        // attackchopper = new JRadioButton("Attack Chopper");
        // attackchopper.setFont(new Font("Arial", Font.PLAIN, 15));
        // attackchopper.setSize(80, 20);
        // attackchopper.setLocation(FIELD_X + 80, 260);
        // c.add(attackchopper);

        gengp = new ButtonGroup();
        gengp.add(male);
        gengp.add(female);
        // gengp.add(attackchopper);

        // --- Date of Birth ---
        dobLabel = new JLabel("DOB");
        dobLabel.setFont(new Font("Arial", Font.PLAIN, 20));
        dobLabel.setSize(LABEL_W, 20);
        dobLabel.setLocation(LABEL_X, 310);
        c.add(dobLabel);

        date = new JComboBox<>(dates);
        date.setFont(new Font("Arial", Font.PLAIN, 15));
        date.setSize(55, FIELD_H);
        date.setLocation(FIELD_X, 310);
        c.add(date);

        month = new JComboBox<>(months);
        month.setFont(new Font("Arial", Font.PLAIN, 15));
        month.setSize(65, FIELD_H);
        month.setLocation(FIELD_X + 60, 310);
        c.add(month);

        year = new JComboBox<>(years);
        year.setFont(new Font("Arial", Font.PLAIN, 15));
        year.setSize(75, FIELD_H);
        year.setLocation(FIELD_X + 130, 310);
        year.setSelectedItem("2000");
        c.add(year);

        // --- Buttons ---
        sub = new JButton("Submit");
        sub.setFont(new Font("Arial", Font.PLAIN, 15));
        sub.setSize(100, 25);
        sub.setLocation(330, 400);
        sub.addActionListener(this);
        c.add(sub);

        reset = new JButton("Reset");
        reset.setFont(new Font("Arial", Font.PLAIN, 15));
        reset.setSize(100, 25);
        reset.setLocation(450, 400);
        reset.addActionListener(this);
        c.add(reset);

        // --- Status label ---
        res = new JLabel("");
        res.setFont(new Font("Arial", Font.PLAIN, 16));
        res.setSize(500, 25);
        res.setLocation(200, 450);
        c.add(res);

        // // --- Dark mode --- (didn't align with the rest of our design language)
        // Color bg = new Color(0x19, 0x1a, 0x1c);
        // Color fieldBg = new Color(0x2b, 0x2b, 0x2b);
        // Color fg = Color.WHITE;
 
        // c.setBackground(bg);
 
        // title.setForeground(fg);
        // usernameLabel.setForeground(fg);
        // passwordLabel.setForeground(fg);
        // confirmPasswordLabel.setForeground(fg);
        // genderLabel.setForeground(fg);
        // dobLabel.setForeground(fg);
        // res.setForeground(fg);
 
        // tusername.setBackground(fieldBg);
        // tusername.setForeground(fg);
        // tusername.setCaretColor(fg);
 
        // tpass.setBackground(fieldBg);
        // tpass.setForeground(fg);
        // tpass.setCaretColor(fg);
 
        // tconfirm.setBackground(fieldBg);
        // tconfirm.setForeground(fg);
        // tconfirm.setCaretColor(fg);
 
        // male.setBackground(bg);
        // male.setForeground(fg);
        // female.setBackground(bg);
        // female.setForeground(fg);
        // // attackchopper.setForeground(fg);
 
        // date.setBackground(fieldBg);
        // date.setForeground(fg);
        // month.setBackground(fieldBg);
        // month.setForeground(fg);
        // year.setBackground(fieldBg);
        // year.setForeground(fg);
 
        // sub.setBackground(fieldBg);
        // sub.setForeground(fg);
        // reset.setBackground(fieldBg);
        // reset.setForeground(fg);

        setVisible(true);
    }

    @Override

    /**
     * public void method that applies action listeners to all the buttons. Essentially adds the logic behind the panel
     */
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == sub) {

            String username = tusername.getText().trim();
            String password = new String(tpass.getPassword());
            String confirm  = new String(tconfirm.getPassword());

            // If submit is pressed without filling the password or username
            if (username.isEmpty() || password.isEmpty()) {
                res.setForeground(Color.RED);
                res.setText("Username and password are required.");
                return;
            }

            if (!password.equals(confirm)) {
                res.setForeground(Color.RED);
                res.setText("Passwords do not match.");
                tpass.setText("");
                tconfirm.setText("");
                return;
            }

            res.setForeground(new Color(0, 150, 0));
            res.setText("Registration successful!");

            // This part writes to the xml file after clicking submit
            try {
                UserXml userXml = new UserXml();
                User newUser = new User(username, password, 0);
                userXml.saveUser(newUser);
            } 

            catch (Exception ex) {
                res.setForeground(Color.RED);
                res.setText("Error saving user.");
            }

        } else if (e.getSource() == reset) {
            tusername.setText("");
            tpass.setText("");
            tconfirm.setText("");
            res.setText("");
            male.setSelected(true);
            date.setSelectedIndex(0);
            month.setSelectedIndex(0);
            year.setSelectedItem("2000");
        }
    }

    // You can test the panel through running this function
    public static void main(String[] args) {
        Registration f = new Registration();
    }
    

}