package jp.webpay.api;

import jp.webpay.exception.ApiConnectionException;
import net.arnx.jsonic.JSON;

import java.util.Map;

public class Account extends Accessor {
    protected Account(WebPayClient client) {
        super(client);
    }

    public jp.webpay.model.Account retrieve() {
        return jp.webpay.model.Account.fromJsonResponse(client, client.get("/account"));
    }

    public boolean deleteData() {
        String json = client.delete("/account/data");

        try {
            Map data = JSON.decode(json);
            return (Boolean)data.get("deleted");
        } catch (net.arnx.jsonic.JSONException ignored) {
            throw ApiConnectionException.jsonException(json);
        } catch (ClassCastException ignored) {
            throw ApiConnectionException.jsonException(json);
        }
    }
}
