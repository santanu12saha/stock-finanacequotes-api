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
import org.santanu.santanubrains.rxjava.domain.MultipleStockQuotesQuery;
import org.santanu.santanubrains.rxjava.exception.InternalErrorException;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;
import org.santanu.santanubrains.rxjava.service.MultipleStockQuoteService;

import com.google.gson.Gson;

import io.reactivex.SingleObserver;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import yahoofinance.histquotes.HistoricalQuote;
import yahoofinance.quotes.stock.StockQuote;

@Path("stock/quotes")
public class StockQuoteMultipleResource {

	private static final Logger logger = Log4jUtil.getLogger(StockQuoteMultipleResource.class);
	private MultipleStockQuoteService multipleStockQuoteService;
	private Gson gson;

	@Inject
	public StockQuoteMultipleResource(MultipleStockQuoteService multipleStockQuoteService, Gson gson) {
		super();
		this.multipleStockQuoteService = multipleStockQuoteService;
		this.gson = gson;
	}

	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockQuotes(@Suspended final AsyncResponse async,
			MultipleStockQuotesQuery multipleStockQuotesQuery) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleStockQuoteService.getMultipleStockQuoteBySymbol(multipleStockQuotesQuery).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<Map<String, StockQuote>>() {

					@Override
					public void onSubscribe(Disposable d) {

					}

					@Override
					public void onSuccess(Map<String, StockQuote> stockQuotesResponse) {
						async.resume(gson.toJson(stockQuotesResponse));
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

	@Path("historicalquotes")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockHistoricalQuote(@Suspended final AsyncResponse async,
			MultipleStockQuotesQuery multipleStockQuotesQuery) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleStockQuoteService.getStockHistoricalQuoteBySymbol(multipleStockQuotesQuery).subscribeOn(Schedulers.io())
				.subscribe(new SingleObserver<Map<String, List<HistoricalQuote>>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Map<String, List<HistoricalQuote>> multipleHistoricalQuoteResponse) {
						async.resume(gson.toJson(multipleHistoricalQuoteResponse));
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

	@Path("historicalquotes/fromdate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockHistoricalQuoteFromdate(@Suspended final AsyncResponse async,
			MultipleStockQuotesQuery multipleStockQuotesQuery, @QueryParam("fromdate") final String fromDate) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleStockQuoteService.getStockHistoricalQuoteByFromDate(multipleStockQuotesQuery, fromDate)
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Map<String, List<HistoricalQuote>>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Map<String, List<HistoricalQuote>> multipleHistoricalQuoteFromDateResponse) {
						async.resume(gson.toJson(multipleHistoricalQuoteFromDateResponse));
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

	@Path("historicalquotes/interval")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockHistoricalQuoteInterval(@Suspended final AsyncResponse async,
			MultipleStockQuotesQuery multipleStockQuotesQuery, @QueryParam("interval") final String interval) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleStockQuoteService.getStockHistoricalQuoteByInterval(multipleStockQuotesQuery, interval.toUpperCase())
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Map<String, List<HistoricalQuote>>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(Map<String, List<HistoricalQuote>> multipleHistoricalQuoteIntervalResponse) {
						async.resume(gson.toJson(multipleHistoricalQuoteIntervalResponse));
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

	@Path("historicalquotes/fromtodate")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getStockHistoricalQuoteFromToDate(@Suspended final AsyncResponse async,
			MultipleStockQuotesQuery multipleStockQuotesQuery, @QueryParam("fromdate") final String fromDate,
			@QueryParam("todate") final String toDate) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleStockQuoteService.getStockHistoricalQuoteByFromToDate(multipleStockQuotesQuery, fromDate, toDate)
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Map<String, List<HistoricalQuote>>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(
							Map<String, List<HistoricalQuote>> multipleHistoricalQuoteFromToDateResponse) {
						async.resume(gson.toJson(multipleHistoricalQuoteFromToDateResponse));
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

	@Path("historicalquotes/fromdate/interval")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockHistoricalQuoteFromInterval(@Suspended final AsyncResponse async,
			MultipleStockQuotesQuery multipleStockQuotesQuery, @QueryParam("fromdate") final String fromDate,
			@QueryParam("interval") final String interval) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleStockQuoteService
				.getStockHistoricalQuoteByFromInterval(multipleStockQuotesQuery, fromDate, interval.toUpperCase())
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Map<String, List<HistoricalQuote>>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(
							Map<String, List<HistoricalQuote>> multipleHistoricalQuoteFromIntervalResponse) {
						async.resume(gson.toJson(multipleHistoricalQuoteFromIntervalResponse));
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

	@Path("historicalquotes/fromtodate/interval")
	@POST
	@Produces(MediaType.APPLICATION_JSON)
	@ManagedAsync
	public void getMultipleStockHistoricalQuoteFromToInterval(@Suspended final AsyncResponse async,
			MultipleStockQuotesQuery multipleStockQuotesQuery, @QueryParam("fromdate") final String fromDate,
			@QueryParam("todate") final String toDate, @QueryParam("interval") final String interval) {

		final CountDownLatch outerLatch = new CountDownLatch(1);

		multipleStockQuoteService
				.getStockHistoricalQuoteByFromToInterval(multipleStockQuotesQuery, fromDate, toDate,
						interval.toUpperCase())
				.subscribeOn(Schedulers.io()).subscribe(new SingleObserver<Map<String, List<HistoricalQuote>>>() {

					@Override
					public void onSubscribe(Disposable d) {
						// TODO Auto-generated method stub

					}

					@Override
					public void onSuccess(
							Map<String, List<HistoricalQuote>> multipleHistoricalQuoteFromToIntervalResponse) {
						async.resume(gson.toJson(multipleHistoricalQuoteFromToIntervalResponse));
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
