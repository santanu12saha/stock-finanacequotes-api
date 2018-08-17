package org.santanu.santanubrains.rxjava.application;

import java.util.Date;

import javax.inject.Singleton;
import javax.ws.rs.ApplicationPath;
import org.apache.log4j.Logger;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.santanu.santanubrains.rxjava.cache.StockCacheStorage;
import org.santanu.santanubrains.rxjava.dataAdapter.FxQuoteAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.FxQuoteAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.FxSymbolsAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.FxSymbolsAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.MultipleFxQuoteAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.MultipleFxQuoteAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockDetailsAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockDetailsAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockDividendAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockDividendAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockNewsAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockNewsAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockQuotePriceAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockQuotePriceAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockQuotesAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockQuotesAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockStatsAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.MultipleStockStatsAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.StockDetailsAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.StockDetailsAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.StockDividendAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.StockDividendAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.StockNewsAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.StockNewsAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.StockQuotePriceAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.StockQuotePriceAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.StockQuotesAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.StockQuotesAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.StockSearchAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.StockSearchAdapterImpl;
import org.santanu.santanubrains.rxjava.dataAdapter.StockStatsAdapter;
import org.santanu.santanubrains.rxjava.dataAdapter.StockStatsAdapterImpl;
import org.santanu.santanubrains.rxjava.exception.GenericExceptionMapper;
import org.santanu.santanubrains.rxjava.exception.InternalErrorExceptionMapper;
import org.santanu.santanubrains.rxjava.exception.StockSymbolNotFoundMapper;
import org.santanu.santanubrains.rxjava.filter.LoggingFilter;
import org.santanu.santanubrains.rxjava.filter.PoweredByResponseFilter;
import org.santanu.santanubrains.rxjava.log4j.Log4jUtil;
import org.santanu.santanubrains.rxjava.resource.FxQuoteMultipleResource;
import org.santanu.santanubrains.rxjava.resource.FxQuoteResource;
import org.santanu.santanubrains.rxjava.resource.FxSymbolResource;
import org.santanu.santanubrains.rxjava.resource.StockDetailResource;
import org.santanu.santanubrains.rxjava.resource.StockDividendMultipleResource;
import org.santanu.santanubrains.rxjava.resource.StockDividendResource;
import org.santanu.santanubrains.rxjava.resource.StockMultipleDetailResource;
import org.santanu.santanubrains.rxjava.resource.StockNewsMultipleResource;
import org.santanu.santanubrains.rxjava.resource.StockNewsResource;
import org.santanu.santanubrains.rxjava.resource.StockQuoteMultipleResource;
import org.santanu.santanubrains.rxjava.resource.StockQuotePriceMultipleResource;
import org.santanu.santanubrains.rxjava.resource.StockQuotePriceResource;
import org.santanu.santanubrains.rxjava.resource.StockQuoteResource;
import org.santanu.santanubrains.rxjava.resource.StockSearchResource;
import org.santanu.santanubrains.rxjava.resource.StockStatsMultipleResource;
import org.santanu.santanubrains.rxjava.resource.StockStatsResource;
import org.santanu.santanubrains.rxjava.service.FxQuoteService;
import org.santanu.santanubrains.rxjava.service.FxQuoteServiceImpl;
import org.santanu.santanubrains.rxjava.service.FxSymbolService;
import org.santanu.santanubrains.rxjava.service.FxSymbolServiceImpl;
import org.santanu.santanubrains.rxjava.service.MultipleFxQuoteService;
import org.santanu.santanubrains.rxjava.service.MultipleFxQuoteServiceImpl;
import org.santanu.santanubrains.rxjava.service.MultipleStockDetailService;
import org.santanu.santanubrains.rxjava.service.MultipleStockDetailServiceImpl;
import org.santanu.santanubrains.rxjava.service.MultipleStockDividendService;
import org.santanu.santanubrains.rxjava.service.MultipleStockDividendServiceImpl;
import org.santanu.santanubrains.rxjava.service.MultipleStockNewService;
import org.santanu.santanubrains.rxjava.service.MultipleStockNewServiceImpl;
import org.santanu.santanubrains.rxjava.service.MultipleStockQuotePriceService;
import org.santanu.santanubrains.rxjava.service.MultipleStockQuotePriceServiceImpl;
import org.santanu.santanubrains.rxjava.service.MultipleStockQuoteService;
import org.santanu.santanubrains.rxjava.service.MultipleStockQuoteServiceImpl;
import org.santanu.santanubrains.rxjava.service.MultipleStockStatsService;
import org.santanu.santanubrains.rxjava.service.MultipleStockStatsServiceImpl;
import org.santanu.santanubrains.rxjava.service.StockDetailService;
import org.santanu.santanubrains.rxjava.service.StockDetailServiceImpl;
import org.santanu.santanubrains.rxjava.service.StockDividendService;
import org.santanu.santanubrains.rxjava.service.StockDividendServiceImpl;
import org.santanu.santanubrains.rxjava.service.StockNewService;
import org.santanu.santanubrains.rxjava.service.StockNewServiceImpl;
import org.santanu.santanubrains.rxjava.service.StockQuotePriceService;
import org.santanu.santanubrains.rxjava.service.StockQuotePriceServiceImpl;
import org.santanu.santanubrains.rxjava.service.StockQuoteService;
import org.santanu.santanubrains.rxjava.service.StockQuoteServiceImpl;
import org.santanu.santanubrains.rxjava.service.StockSearchService;
import org.santanu.santanubrains.rxjava.service.StockSearchServiceImpl;
import org.santanu.santanubrains.rxjava.service.StockStatsService;
import org.santanu.santanubrains.rxjava.service.StockStatsServiceImpl;

import com.google.gson.Gson;

@ApplicationPath("api")
public class RxJavaYahooFinanceApi extends ResourceConfig {

	private static final Logger logger = Log4jUtil.getLogger(RxJavaYahooFinanceApi.class);

	public RxJavaYahooFinanceApi() {

		logger.info("Restful rxjava yahoo finance application initializing : " + new Date().toString());

		register(LoggingFilter.class);
		register(PoweredByResponseFilter.class);
		register(StockDetailResource.class);
		register(StockSymbolNotFoundMapper.class);
		register(GenericExceptionMapper.class);
		register(InternalErrorExceptionMapper.class);
		register(StockMultipleDetailResource.class);
		register(StockQuoteResource.class);
		register(StockQuoteMultipleResource.class);
		register(FxSymbolResource.class);
		register(FxQuoteResource.class);
		register(FxQuoteMultipleResource.class);
		register(StockQuotePriceResource.class);
		register(StockQuotePriceMultipleResource.class);
		register(StockDividendResource.class);
		register(StockDividendMultipleResource.class);
		register(StockSearchResource.class);
		register(StockNewsResource.class);
		register(StockNewsMultipleResource.class);
		register(StockStatsResource.class);
		register(StockStatsMultipleResource.class);

		register(new AbstractBinder() {

			@Override
			protected void configure() {
				bindAsContract(Gson.class);
				bindAsContract(StockCacheStorage.class).in(Singleton.class);
				bind(StockDetailsAdapterImpl.class).to(StockDetailsAdapter.class);
				bind(StockDetailServiceImpl.class).to(StockDetailService.class);
				bind(MultipleStockDetailsAdapterImpl.class).to(MultipleStockDetailsAdapter.class);
				bind(MultipleStockDetailServiceImpl.class).to(MultipleStockDetailService.class);
				bind(StockQuotesAdapterImpl.class).to(StockQuotesAdapter.class);
				bind(StockQuoteServiceImpl.class).to(StockQuoteService.class);
				bind(MultipleStockQuotesAdapterImpl.class).to(MultipleStockQuotesAdapter.class);
				bind(MultipleStockQuoteServiceImpl.class).to(MultipleStockQuoteService.class);
				bind(FxSymbolsAdapterImpl.class).to(FxSymbolsAdapter.class);
				bind(FxSymbolServiceImpl.class).to(FxSymbolService.class);
				bind(FxQuoteAdapterImpl.class).to(FxQuoteAdapter.class);
				bind(FxQuoteServiceImpl.class).to(FxQuoteService.class);
				bind(MultipleFxQuoteAdapterImpl.class).to(MultipleFxQuoteAdapter.class);
				bind(MultipleFxQuoteServiceImpl.class).to(MultipleFxQuoteService.class);
				bind(StockQuotePriceAdapterImpl.class).to(StockQuotePriceAdapter.class);
				bind(StockQuotePriceServiceImpl.class).to(StockQuotePriceService.class);
				bind(MultipleStockQuotePriceAdapterImpl.class).to(MultipleStockQuotePriceAdapter.class);
				bind(MultipleStockQuotePriceServiceImpl.class).to(MultipleStockQuotePriceService.class);
				bind(StockDividendAdapterImpl.class).to(StockDividendAdapter.class);
				bind(StockDividendServiceImpl.class).to(StockDividendService.class);
				bind(MultipleStockDividendAdapterImpl.class).to(MultipleStockDividendAdapter.class);
				bind(MultipleStockDividendServiceImpl.class).to(MultipleStockDividendService.class);
				bind(StockSearchAdapterImpl.class).to(StockSearchAdapter.class);
				bind(StockSearchServiceImpl.class).to(StockSearchService.class);
				bind(StockNewsAdapterImpl.class).to(StockNewsAdapter.class);
				bind(StockNewServiceImpl.class).to(StockNewService.class);
				bind(MultipleStockNewsAdapterImpl.class).to(MultipleStockNewsAdapter.class);
				bind(MultipleStockNewServiceImpl.class).to(MultipleStockNewService.class);
				bind(StockStatsAdapterImpl.class).to(StockStatsAdapter.class);
				bind(StockStatsServiceImpl.class).to(StockStatsService.class);
				bind(MultipleStockStatsAdapterImpl.class).to(MultipleStockStatsAdapter.class);
				bind(MultipleStockStatsServiceImpl.class).to(MultipleStockStatsService.class);

			}
		});

	}
}
