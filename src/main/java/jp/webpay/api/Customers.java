package jp.webpay.api;

import jp.webpay.exception.ApiConnectionException;
import jp.webpay.model.Customer;
import jp.webpay.model.CustomerList;
import jp.webpay.model.RetrievedCustomer;
import jp.webpay.request.CustomerRequest;
import jp.webpay.request.ListRequest;
import lombok.NonNull;
import net.arnx.jsonic.JSON;

import java.util.Map;

public class Customers extends Accessor {
    Customers(@NonNull WebPayClient client) {
        super(client);
    }

    public Customer create(@NonNull CustomerRequest request) {
        return Customer.fromJsonResponse(client, client.post("/customers", request.toForm()));
    }

    public RetrievedCustomer retrieve(@NonNull String id) {
        assertId(id);
        return RetrievedCustomer.fromJsonResponse(client, client.get("/customers/" + id));
    }

    public Customer update(@NonNull String id, @NonNull CustomerRequest request) {
        assertId(id);
        return Customer.fromJsonResponse(client, client.post("/customers/" + id, request.toForm()));
    }

    public boolean delete(@NonNull String id) {
        assertId(id);
        String json = client.delete("/customers/" + id);

        try {
            Map data = JSON.decode(json);
            return (Boolean)data.get("deleted");
        } catch (net.arnx.jsonic.JSONException ignored) {
            throw ApiConnectionException.jsonException(json);
        } catch (ClassCastException ignored) {
            throw ApiConnectionException.jsonException(json);
        }
    }

    public CustomerList all() {
        return all(new ListRequest());
    }

    public CustomerList all(@NonNull ListRequest request) {
        return CustomerList.fromJsonResponse(client, client.get("/customers", request.toForm()));
    }
}
