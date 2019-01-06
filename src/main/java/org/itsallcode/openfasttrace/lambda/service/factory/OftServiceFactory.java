package org.itsallcode.openfasttrace.lambda.service.factory;

import java.util.Map;

import org.itsallcode.openfasttrace.lambda.service.OftService;

import com.github.kaklakariada.aws.lambda.model.request.ApiGatewayRequest;
import com.github.kaklakariada.aws.lambda.service.ServiceFactory;
import com.github.kaklakariada.aws.lambda.service.ServiceRegistry;

public class OftServiceFactory implements ServiceFactory<OftServiceParam>
{
    @Override
    public void registerServices(ServiceRegistry registry, OftServiceParam params)
    {
        registry.addService(OftService.class, OftService::new);
    }

    @Override
    public OftServiceParam extractServiceParams(ApiGatewayRequest request, Map<String, String> env)
    {
        return new OftServiceParam(request.getStageVariable("stage"));
    }
}
