package org.itsallcode.openfasttrace.lambda;

import javax.inject.Inject;

import org.itsallcode.openfasttrace.lambda.service.OftService;
import org.itsallcode.openfasttrace.lambda.service.factory.OftServiceFactory;

import com.github.kaklakariada.aws.lambda.LambdaRequestHandler;
import com.github.kaklakariada.aws.lambda.controller.LambdaController;
import com.github.kaklakariada.aws.lambda.controller.RequestBody;
import com.github.kaklakariada.aws.lambda.controller.RequestHandlerMethod;

public class TraceLambda extends LambdaRequestHandler
{
    protected TraceLambda()
    {
        super(new TraceController(), new OftServiceFactory());
    }

    private static class TraceController implements LambdaController
    {
        @Inject
        private OftService oftService;

        @RequestHandlerMethod
        public TraceResponse trace(@RequestBody TraceRequest request)
        {
            return oftService.trace(request);
        }
    }
}
