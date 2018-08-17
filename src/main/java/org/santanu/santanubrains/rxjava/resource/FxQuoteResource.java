package org.santanu.santanubrains.rxjava.resource;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.santanu.santanubrains.rxjava.exception.InternalErrorException;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;
import org.santanu.santanubrains.rxjava.service.FxQuoteService;

import com.google.gson.Gson;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import yahoofinance.quotes.fx.FxQuote;

@Path("stock/fxquote/{symbol}")
public class FxQuoteResource {

	private static final Logger logger = Log4jUtil.getLogger(FxQuoteResource.class);
	private FxQuoteService fxQuoteService;
	private Gson gson;

	@Inject
	public FxQuoteResource(FxQuoteService fxQuoteService, Gson gson) {
		super();
		this.fxQuoteService = fxQuoteService;
		this.gson = gson;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getFxQuoteBySymbol(@Suspended final AsyncResponse async, @PathParam("symbol") final String symbol) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		fxQuoteService.getFxQuoteBySymbol(symbol).subscribeOn(Schedulers.io()).subscribe(new SingleObserver<FxQuote>() {

			@Override
			public void onSubscribe(Disposable d) {
				// TODO Auto-generated method stub

			}

			@Override
			public void onSuccess(FxQuote fxQuoteResponse) {
				async.resume(gson.toJson(fxQuoteResponse));
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
