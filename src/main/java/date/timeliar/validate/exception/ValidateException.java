package date.timeliar.validate.exception;

/**
 * @Author: TimeLiar
 * @CreateTime: 2016-12-11 11:21
 * @Description: auth-center
 */
public class ValidateException extends Exception {
    private String illegal;
    private String missing;

    public ValidateException(String illegal, String missing) {
        super("wrong input");
        this.illegal = illegal;
        this.missing = missing;
    }

    public String getIllegal() {
        return illegal;
    }

    public void setIllegal(String illegal) {
        this.illegal = illegal;
    }

    public String getMissing() {
        return missing;
    }

    public void setMissing(String missing) {
        this.missing = missing;
    }
}
