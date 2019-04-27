package forms;

import org.hibernate.validator.constraints.SafeHtml;

public class SearchForm {

    String keyword;

    @SafeHtml(whitelistType = SafeHtml.WhiteListType.NONE)
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }
}
