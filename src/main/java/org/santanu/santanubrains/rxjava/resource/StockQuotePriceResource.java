package org.santanu.santanubrains.rxjava.resource;

import java.math.BigDecimal;
import java.util.Map;
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
import org.santanu.santanubrains.rxjava.service.StockQuotePriceService;

import com.google.gson.Gson;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@Path("stock/price/{symbol}")
public class StockQuotePriceResource {

	private static final Logger logger = Log4jUtil.getLogger(StockQuotePriceResource.class);
	private StockQuotePriceService stockQuotePriceService;
	private Gson gson;

	@Inject
	public StockQuotePriceResource(StockQuotePriceService stockQuotePriceService, Gson gson) {
		super();
		this.stockQuotePriceService = stockQuotePriceService;
		this.gson = gson;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockQuotePrice(@Suspended final AsyncResponse async, @PathParam("symbol") final String symbol) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockQuotePriceService.getStockQuotePriceBySymbol(symbol.toUpperCase()).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<Map<String, BigDecimal>>() {

					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onSuccess(Map<String, BigDecimal> stockQuotePriceResponse) {
						async.resume(gson.toJson(stockQuotePriceResponse));
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
