package org.santanu.santanubrains.rxjava.resource;

import java.util.Map;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.santanu.santanubrains.rxjava.exception.InternalErrorException;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;
import org.santanu.santanubrains.rxjava.service.FxSymbolService;

import com.google.gson.Gson;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;


@Path("stock/fxsymbols")
public class FxSymbolResource {

	private static final Logger logger = Log4jUtil.getLogger(FxSymbolResource.class);
	private FxSymbolService fxSymbolService;
	private Gson gson;

	@Inject
	public FxSymbolResource(FxSymbolService fxSymbolService, Gson gson) {
		super();
		this.fxSymbolService = fxSymbolService;
		this.gson = gson;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getFxSymbols(@Suspended final AsyncResponse async) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		fxSymbolService.getFxSymbols().subscribe(new SingleObserver<Map<String, Set<String>>>() {

			@Override
			public void onSubscribe(Disposable d) {

			}

			@Override
			public void onSuccess(Map<String, Set<String>> fxSymbolResponse) {
				async.resume(gson.toJson(fxSymbolResponse));
				outerLatch.countDown();
			}

			@Override
			public void onError(Throwable errorResponse) {
				logger.error(errorResponse);
				async.resume(errorResponse);
				outerLatch.countDown();

			}
		});

		try {
			if (!outerLatch.await(10, TimeUnit.SECONDS)) {
				async.resume(new InternalErrorException());
			}
		} catch (Exception e) {
			async.resume(new InternalErrorException());
		}
	}

}
