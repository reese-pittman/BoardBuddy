public class BoardGame {

    /**
     * 
     * @param sId
     */
    public void setId(String sId) {
        id = sId;
    }
    /**
     * 
     * @param sName
     */
    public void setName(String sName) {
        name = sName;
    }
    /**
     * 
     * @param sMin
     */
    public void setMin(int sMin) {
        minPlayers = sMin;
    }
    /**
     * 
     * @param sMax
     */
    public void setMax(int sMax) {
        maxPlayers = sMax;
    }
    /**
     * 
     * @param sPlay
     */
    public void setPlay(int sPlay) {
        playTime = sPlay;
    }
    /**
     * 
     * @param sYear
     */
    public void setYear(int sYear) {
        year = sYear;
    }
    /**
     * 
     * @param sImage
     */
    public void setImage(String sImage) {
        image = sImage;
    }
    /**
     * 
     * @param sThumbnail
     */
    public void setThumbnail(String sThumbnail) {
        thumbnail = sThumbnail;
    }

    private String id;
    private String name;
    private int minPlayers;
    private int maxPlayers;
    private int playTime;
    private int year;
    private String image;
    private String thumbnail;
}
