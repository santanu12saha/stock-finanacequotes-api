package org.santanu.santanubrains.rxjava.resource;

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
import org.santanu.santanubrains.rxjava.service.MultipleStockDetailService;
import com.google.gson.Gson;
import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import yahoofinance.Stock;

@Path("stocks")
public class StockMultipleDetailResource {

	private static final Logger logger = Log4jUtil.getLogger(StockMultipleDetailResource.class);
	private MultipleStockDetailService multipleStockDetailService;
	private Gson gson;

	@Inject
	public StockMultipleDetailResource(MultipleStockDetailService multipleStockDetailService, Gson gson) {
		super();
		this.multipleStockDetailService = multipleStockDetailService;
		this.gson = gson;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockDetails(@Suspended final AsyncResponse async, MultipleStockQuery multipleStockQuery) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleStockDetailService.getMultipleStockDetailBySymbols(multipleStockQuery).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<Map<String, Stock>>() {

					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onSuccess(Map<String, Stock> stocksResponse) {
						async.resume(gson.toJson(stocksResponse));
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

	@Path("includehistorical")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockDetailWithHistoricalData(@Suspended final AsyncResponse async,
			MultipleStockQuery multipleStockQuery, @QueryParam("include") final boolean includeHistorical) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleStockDetailService
				.getMultipleStockDetailsWithIncludeHistoricalDataBySymbol(multipleStockQuery, includeHistorical)
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Map<String, Stock>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Map<String, Stock> multipleStockWithHistoricalResponse) {
						async.resume(gson.toJson(multipleStockWithHistoricalResponse));
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
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockDetailByFromDate(@Suspended final AsyncResponse async,
			MultipleStockQuery multipleStockQuery, @QueryParam("fromdate") final String fromDate) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleStockDetailService.getMultipleStockByFromDate(multipleStockQuery, fromDate).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<Map<String, Stock>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Map<String, Stock> multipleStockFromDateResponse) {
						async.resume(gson.toJson(multipleStockFromDateResponse));
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
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockDetailByInterval(@Suspended final AsyncResponse async,
			MultipleStockQuery multipleStockQuery, @QueryParam("interval") final String interval) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleStockDetailService.getMultipleStockByInterval(multipleStockQuery, interval.toUpperCase())
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Map<String, Stock>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Map<String, Stock> multipleStockIntervalResponse) {
						async.resume(gson.toJson(multipleStockIntervalResponse));
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
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockDetailByFromToDate(@Suspended final AsyncResponse async,
			MultipleStockQuery multipleStockQuery, @QueryParam("fromdate") final String fromDate,
			@QueryParam("todate") final String toDate) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleStockDetailService.getMultipleStockByFromToDate(multipleStockQuery, fromDate, toDate)
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Map<String, Stock>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Map<String, Stock> multipleStockFromToDateResponse) {
						async.resume(gson.toJson(multipleStockFromToDateResponse));
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
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockDetailByFromInterval(@Suspended final AsyncResponse async,
			MultipleStockQuery multipleStockQuery, @QueryParam("fromdate") final String fromDate,
			@QueryParam("interval") final String interval) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleStockDetailService.getMultipleStockByFromInterval(multipleStockQuery, fromDate, interval.toUpperCase())
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Map<String, Stock>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Map<String, Stock> multipleStockFromIntervalResponse) {
						async.resume(gson.toJson(multipleStockFromIntervalResponse));
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
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockDetailByFromToDateInterval(@Suspended final AsyncResponse async,
			MultipleStockQuery multipleStockQuery, @QueryParam("fromdate") final String fromDate,
			@QueryParam("todate") final String toDate, @QueryParam("interval") final String interval) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleStockDetailService
				.getMultipleStockByFromToDateInterval(multipleStockQuery, fromDate, toDate, interval.toUpperCase())
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Map<String, Stock>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Map<String, Stock> multipleStockBFromToDateIntervalResponse) {
						async.resume(gson.toJson(multipleStockBFromToDateIntervalResponse));
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
