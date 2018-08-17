package org.santanu.santanubrains.rxjava.resource;

import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.santanu.santanubrains.rxjava.domain.FxMultipleQuoteQuery;
import org.santanu.santanubrains.rxjava.exception.InternalErrorException;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;
import org.santanu.santanubrains.rxjava.service.MultipleFxQuoteService;

import com.google.gson.Gson;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import yahoofinance.quotes.fx.FxQuote;

@Path("stock/fxquotes")
public class FxQuoteMultipleResource {

	private static final Logger logger = Log4jUtil.getLogger(FxQuoteMultipleResource.class);
	private MultipleFxQuoteService multipleFxQuoteService;
	private Gson gson;

	@Inject
	public FxQuoteMultipleResource(MultipleFxQuoteService multipleFxQuoteService, Gson gson) {
		super();
		this.multipleFxQuoteService = multipleFxQuoteService;
		this.gson = gson;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleFxQuotes(@Suspended final AsyncResponse async, FxMultipleQuoteQuery fxMultipleQuoteQuery) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleFxQuoteService.getMultipleFxQuotesBySymbol(fxMultipleQuoteQuery).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<Map<String, FxQuote>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Map<String, FxQuote> fxQuotesResponse) {
						async.resume(gson.toJson(fxQuotesResponse));
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
