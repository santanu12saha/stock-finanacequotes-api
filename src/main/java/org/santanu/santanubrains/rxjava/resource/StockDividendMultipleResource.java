package org.santanu.santanubrains.rxjava.resource;

import java.util.List;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.container.AsyncResponse;
import javax.ws.rs.container.Suspended;
import javax.ws.rs.core.MediaType;

import org.apache.log4j.Logger;
import org.glassfish.jersey.server.ManagedAsync;
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuery;
import org.santanu.santanubrains.rxjava.exception.InternalErrorException;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;
import org.santanu.santanubrains.rxjava.service.MultipleStockDividendService;

import com.google.gson.Gson;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import yahoofinance.histquotes2.HistoricalDividend;
import yahoofinance.quotes.stock.StockDividend;

@Path("stock/dividends")
public class StockDividendMultipleResource {

	private static final Logger logger = Log4jUtil.getLogger(StockDividendMultipleResource.class);
	private MultipleStockDividendService multiStockDividendService;
	private Gson gson;

	@Inject
	public StockDividendMultipleResource(MultipleStockDividendService multiStockDividendService, Gson gson) {
		super();
		this.multiStockDividendService = multiStockDividendService;
		this.gson = gson;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockDividend(@Suspended final AsyncResponse async, MultipleStockQuery multipleStockQuery) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multiStockDividendService.getMultipleStockDividendBySymbol(multipleStockQuery).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<Map<String, StockDividend>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Map<String, StockDividend> stockDividendResponse) {
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

	@Path("history")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockDividendHistory(@Suspended final AsyncResponse async,
			MultipleStockQuery multipleStockQuery) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multiStockDividendService.getMultipleStockHistoricalDividendBySymbol(multipleStockQuery)
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Map<String, List<HistoricalDividend>>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Map<String, List<HistoricalDividend>> listHistoricalDividendResponse) {
						async.resume(gson.toJson(listHistoricalDividendResponse));
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

	@Path("historyfromdate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockDividendHistoryFromDate(@Suspended final AsyncResponse async,
			MultipleStockQuery multipleStockQuery, @QueryParam("fromdate") final String fromDate) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multiStockDividendService.getMultipleStockHisDivByFromDate(multipleStockQuery, fromDate)
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Map<String, List<HistoricalDividend>>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(
							Map<String, List<HistoricalDividend>> listHistoricalDividendFromDateResponse) {
						async.resume(gson.toJson(listHistoricalDividendFromDateResponse));
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

	@Path("historyfromtodate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockDividendHistoryFromToDate(@Suspended final AsyncResponse async,
			MultipleStockQuery multipleStockQuery, @QueryParam("fromdate") final String fromDate,
			@QueryParam("todate") final String toDate) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multiStockDividendService.getMultipleStockHisDivByFromToDate(multipleStockQuery, fromDate, toDate)
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Map<String, List<HistoricalDividend>>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(
							Map<String, List<HistoricalDividend>> listHistoricalDividendFromToDateResponse) {
						async.resume(gson.toJson(listHistoricalDividendFromToDateResponse));
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
