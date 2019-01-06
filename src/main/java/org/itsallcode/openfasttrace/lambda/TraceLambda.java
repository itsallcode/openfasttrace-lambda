package org.itsallcode.openfasttrace.lambda;

import java.util.function.Supplier;

import javax.inject.Inject;

import org.itsallcode.openfasttrace.lambda.service.OftService;
import org.itsallcode.openfasttrace.lambda.service.factory.OftServiceFactory;

import com.github.kaklakariada.aws.lambda.LambdaRequestHandler;
import com.github.kaklakariada.aws.lambda.controller.LambdaController;
import com.github.kaklakariada.aws.lambda.controller.RequestBody;
import com.github.kaklakariada.aws.lambda.controller.RequestHandlerMethod;

public class TraceLambda extends LambdaRequestHandler
{
    public TraceLambda()
    {
        super(new TraceController(), new OftServiceFactory());
    }

    public static class TraceController implements LambdaController
    {
        @Inject
        private Supplier<OftService> oftService;

        @RequestHandlerMethod
        public TraceResponse trace(@RequestBody TraceRequest request)
        {
            return oftService.get().trace(request);
        }
    }
}
