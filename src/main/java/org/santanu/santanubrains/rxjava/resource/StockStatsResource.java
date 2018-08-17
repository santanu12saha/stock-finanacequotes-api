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
import org.santanu.santanubrains.rxjava.service.StockStatsService;

import com.google.gson.Gson;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import yahoofinance.quotes.stock.StockStats;

@Path("stock/statistic/{symbol}")
public class StockStatsResource {

	private static final Logger logger = Log4jUtil.getLogger(StockStatsResource.class);
	private StockStatsService stockStatsService;
	private Gson gson;

	@Inject
	public StockStatsResource(StockStatsService stockStatsService, Gson gson) {
		super();
		this.stockStatsService = stockStatsService;
		this.gson = gson;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockStatisticBySymbol(@Suspended final AsyncResponse async,
			@PathParam("symbol") final String symbol) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockStatsService.getStockStatisticBySymbol(symbol.toUpperCase()).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<StockStats>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(StockStats stockStatisticResponse) {
						async.resume(gson.toJson(stockStatisticResponse));
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
