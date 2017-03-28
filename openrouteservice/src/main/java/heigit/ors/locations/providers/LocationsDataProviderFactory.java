/*|----------------------------------------------------------------------------------------------
 *|														Heidelberg University
 *|	  _____ _____  _____      _                     	Department of Geography		
 *|	 / ____|_   _|/ ____|    (_)                    	Chair of GIScience
 *|	| |  __  | | | (___   ___ _  ___ _ __   ___ ___ 	(C) 2014-2016
 *|	| | |_ | | |  \___ \ / __| |/ _ \ '_ \ / __/ _ \	
 *|	| |__| |_| |_ ____) | (__| |  __/ | | | (_|  __/	Berliner Strasse 48								
 *|	 \_____|_____|_____/ \___|_|\___|_| |_|\___\___|	D-69120 Heidelberg, Germany	
 *|	        	                                       	http://www.giscience.uni-hd.de
 *|								
 *|----------------------------------------------------------------------------------------------*/
package heigit.ors.locations.providers;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ServiceLoader;

import heigit.ors.locations.providers.LocationsDataProvider;

public class LocationsDataProviderFactory 
{
	private static Map<String, LocationsDataProviderItem> _providers;
    private static Object _lockObj;

	static
	{
    	_lockObj = new Object();
		_providers = new HashMap<String, LocationsDataProviderItem>();
		
		synchronized(_lockObj)
		{
			ServiceLoader<LocationsDataProvider> loader = ServiceLoader.load(LocationsDataProvider.class);

			Iterator<LocationsDataProvider> entries = loader.iterator();
			while (entries.hasNext()) {
				LocationsDataProvider entry = entries.next();
				String name = entry.getName().toLowerCase();
				
				if (!_providers.containsKey(name))
				{
					try {
						LocationsDataProvider provider = entry.getClass().newInstance();
						LocationsDataProviderItem item = new LocationsDataProviderItem(provider);
						_providers.put(name, item);
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	public static LocationsDataProvider getProvider(String name, Map<String, Object> parameters) throws Exception
	{
		if (name == null)
			throw new Exception("Data provider is not defined.");
		
		LocationsDataProvider provider = null;

		synchronized(_lockObj)
		{
			String pname = name.toLowerCase();

			LocationsDataProviderItem item = _providers.get(pname);

			if (item == null)
				throw new Exception("Unable to find a data provider with name '" + name + "'.");

			provider = item.getProvider();
			
			if (!item.getIsInitialized())
			{
				provider.init(parameters);
				item.setIsInitialized(true);
			}
		}

		return provider;
	}
	
	public static void releaseProviders() throws Exception
	{
		synchronized(_lockObj)
		{
			for(Map.Entry<String, LocationsDataProviderItem> item: _providers.entrySet())
			{
				if (item.getValue().getIsInitialized())
					item.getValue().getProvider().close();
			}
		}
	}
}
