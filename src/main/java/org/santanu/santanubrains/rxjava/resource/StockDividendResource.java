package org.santanu.santanubrains.rxjava.resource;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.santanu.santanubrains.rxjava.exception.InternalErrorException;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;
import org.santanu.santanubrains.rxjava.service.StockDividendService;

import com.google.gson.Gson;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockDividend;

@Path("stock/dividend")
public class StockDividendResource {

	private static final Logger logger = Log4jUtil.getLogger(StockDividendResource.class);
	private StockDividendService stockDividendService;
	private Gson gson;

	@Inject
	public StockDividendResource(StockDividendService stockDividendService, Gson gson) {
		super();
		this.stockDividendService = stockDividendService;
		this.gson = gson;
	}

	@Path("{symbol}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockDividend(@Suspended final AsyncResponse async, @PathParam("symbol") final String symbol) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockDividendService.getStockDividendBySymbol(symbol.toUpperCase()).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<StockDividend>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(StockDividend stockDividendResponse) {
						async.resume(gson.toJson(stockDividendResponse));
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

	@Path("history/{symbol}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockDividendHistory(@Suspended final AsyncResponse async,
			@PathParam("symbol") final String symbol) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockDividendService.getStockDividendHistoryBySymbol(symbol.toUpperCase()).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<List<HistoricalDividend>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<HistoricalDividend> historicalDividendResponse) {
						async.resume(gson.toJson(historicalDividendResponse));
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

	@Path("historyfromdate/{symbol}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockDividendHistoryByFromDate(@Suspended final AsyncResponse async,
			@PathParam("symbol") final String symbol, @QueryParam("fromdate") final String fromDate) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockDividendService.getStockDivHisByFromDate(symbol.toUpperCase(), fromDate).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<List<HistoricalDividend>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<HistoricalDividend> historicalDividendFromDateResponse) {
						async.resume(gson.toJson(historicalDividendFromDateResponse));
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

	@Path("historyfromtodate/{symbol}")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockDividendHistoryFromToDate(@Suspended final AsyncResponse async,
			@PathParam("symbol") final String symbol, @QueryParam("fromdate") final String fromDate,
			@QueryParam("todate") final String toDate) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockDividendService.getStockDivHisByFromToDate(symbol.toUpperCase(), fromDate, toDate)
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<List<HistoricalDividend>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(List<HistoricalDividend> historicalDividendFromToDateResponse) {
						async.resume(gson.toJson(historicalDividendFromToDateResponse));
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
