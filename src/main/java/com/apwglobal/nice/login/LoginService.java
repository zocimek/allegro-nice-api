package com.apwglobal.nice.login;

import com.apwglobal.nice.exception.AllegroExecutor;
import com.apwglobal.nice.service.AbstractService;
import com.apwglobal.nice.service.AllegroSession;
import com.apwglobal.nice.service.Configuration;
import com.apwglobal.nice.util.VersionUtil;
import pl.allegro.webapi.DoLoginRequest;
import pl.allegro.webapi.DoLoginResponse;
import pl.allegro.webapi.ServicePort;

public class LoginService extends AbstractService {

    public LoginService(ServicePort allegro, Credentials cred, Configuration conf) {
        super(allegro, cred, conf);
    }

    public AllegroSession login() {
        long version = AllegroExecutor.execute(() -> VersionUtil.getVersion(allegro, conf.getCountryId(), cred.getKey()));

        DoLoginRequest request = new DoLoginRequest(cred.getUsername(), cred.getPassowrd(), conf.getCountryId(), cred.getKey(), version);
        DoLoginResponse response = AllegroExecutor.execute(() -> allegro.doLogin(request));

        return new AllegroSession.Builder()
                .sessionId(response.getSessionHandlePart())
                .userId(response.getUserId())
                .build();
    }
}