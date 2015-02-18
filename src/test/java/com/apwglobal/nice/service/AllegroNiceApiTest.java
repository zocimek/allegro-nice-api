package com.apwglobal.nice.service;

import com.apwglobal.nice.deal.Deal;
import com.apwglobal.nice.journal.Journal;
import com.apwglobal.nice.login.AbstractLoggedServiceBaseTest;
import org.junit.Assert;
import org.junit.Test;
import pl.allegro.webapi.ItemPostBuyDataStruct;
import rx.Observable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

public class AllegroNiceApiTest extends AbstractLoggedServiceBaseTest {

    @Test
    public void shouldNotReturnSessionId() {
        Assert.assertNull(api.getSession());
    }

    @Test
    public void shouldReturnSessionId() {
        assertNotNull(api.login().getSession());
    }

    @Test
    public void shouldReturnStatus() {
        assertNotNull(api.getStatus());
    }

    @Test
    public void shouldReturnAllegroMessages()  {
        assertTrue(api.getAllMessages(LocalDateTime.now().minusDays(1000)).size() > 0);
    }

    @Test
    public void shouldReturnListOfClientsData() {
        api.login();
        Observable<Journal> o = api.getJournal(0);
        List<Journal> jourlans = o.limit(10)
                .toList()
                .toBlocking()
                .single();

        assertNotNull(jourlans);

        List<ItemPostBuyDataStruct> postBuy = jourlans
                .stream()
                .flatMap(j -> api.getClientsDate(j.getItemId()).stream())
                .collect(Collectors.toList());

        assertNotNull(postBuy);
    }

    @Test
    public void shouldResturnListOfDeals() {
        api.login();
        Observable<Deal> o = api.getDeals(0);
        List<Deal> deals = o
                .toList()
                .toBlocking()
                .single();

        assertNotNull(deals);
    }
}
