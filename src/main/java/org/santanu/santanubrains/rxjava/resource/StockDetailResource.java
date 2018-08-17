package org.santanu.santanubrains.rxjava.resource;

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
import org.santanu.santanubrains.rxjava.service.StockDetailService;
import com.google.gson.Gson;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import yahoofinance.Stock;

@Path("stock/{symbol}")
public class StockDetailResource {

	private static final Logger logger = Log4jUtil.getLogger(StockDetailResource.class);
	private StockDetailService stockDetailService;
	private Gson gson;

	@Inject
	public StockDetailResource(StockDetailService stockDetailService, Gson gson) {
		this.stockDetailService = stockDetailService;
		this.gson = gson;
	}

	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockDetail(@Suspended final AsyncResponse async, @PathParam("symbol") final String symbol) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockDetailService.getStockDetailBySymbols(symbol.toUpperCase()).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<Stock>() {

					@Override
					public void onError(Throwable errorResponse) {
						logger.error(errorResponse);
						async.resume(errorResponse);
						outerLatch.countDown();

					}

					@Override
					public void onSubscribe(Disposable disposable) {

					}

					@Override
					public void onSuccess(Stock stockResponse) {
						async.resume(gson.toJson(stockResponse));
						outerLatch.countDown();
						// sleep(10000);

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

	@Path("includehistorical")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockDetailWithHistoricalData(@Suspended final AsyncResponse async,
			@PathParam("symbol") final String symbol, @QueryParam("include") final boolean includeHistorical) {

		final CountDownLatch outerLatch = new CountDownLatch(1);
		
		stockDetailService.getStockDetailsWithIncludeHistoricalDataBySymbol(symbol.toUpperCase(), includeHistorical)
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Stock>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Stock stockWithHistoricalResponse) {
						async.resume(gson.toJson(stockWithHistoricalResponse));
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

	@Path("fromdate")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockDetailByFromDate(@Suspended final AsyncResponse async, @PathParam("symbol") final String symbol,
			@QueryParam("fromdate") final String fromDate) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockDetailService.getStockByFromDate(symbol.toUpperCase(), fromDate).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<Stock>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Stock stockByFromDateResponse) {
						async.resume(gson.toJson(stockByFromDateResponse));
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

	@Path("interval")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockDetailByInterval(@Suspended final AsyncResponse async, @PathParam("symbol") final String symbol,
			@QueryParam("interval") final String interval) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockDetailService.getStockByInterval(symbol.toUpperCase(), interval.toUpperCase()).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<Stock>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Stock stockByIntervalResponse) {
						async.resume(gson.toJson(stockByIntervalResponse));
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

	@Path("fromtodate")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockDetailByFromToDate(@Suspended final AsyncResponse async,
			@PathParam("symbol") final String symbol, @QueryParam("fromdate") final String fromDate,
			@QueryParam("todate") final String toDate) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockDetailService.getStockByFromToDate(symbol.toUpperCase(), fromDate, toDate).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<Stock>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Stock stockByFromToDateResponse) {
						async.resume(gson.toJson(stockByFromToDateResponse));
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

	@Path("frominterval")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockDetailByFromInterval(@Suspended final AsyncResponse async,
			@PathParam("symbol") final String symbol, @QueryParam("fromdate") final String fromDate,
			@QueryParam("interval") final String interval) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockDetailService.getStockByFromInterval(symbol.toUpperCase(), fromDate, interval.toUpperCase())
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Stock>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Stock stockByFromIntervalResponse) {
						async.resume(gson.toJson(stockByFromIntervalResponse));
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

	@Path("fromtointerval")
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockDetailByFromToDateInterval(@Suspended final AsyncResponse async,
			@PathParam("symbol") final String symbol, @QueryParam("fromdate") final String fromDate,
			@QueryParam("todate") final String toDate, @QueryParam("interval") final String interval) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		stockDetailService.getStockByFromToDateInterval(symbol.toUpperCase(), fromDate, toDate, interval.toUpperCase())
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Stock>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Stock stockByFromToDateIntervalResponse) {
						async.resume(gson.toJson(stockByFromToDateIntervalResponse));
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

	protected void sleep(int ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
