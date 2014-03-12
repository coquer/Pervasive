package dk.jycr753.location;

public class CalculateDistance {
	
	public static double getDistance( double lat2 ,double lon2){
		double lat1 = 55.669669;
		double lon1 = 12.5880716;
		double R = 6371;
		double dlat = Math.toRadians( lat2 - lat1);
		double dlon = Math.toRadians(lon2 - lon1);
		double a = Math.sin(dlat/2) * Math.sin(dlat/2) + Math.sin(dlon/2) * Math.sin(dlon/2) * Math.cos(lat2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		double d = R * c;
		return d;
	}
}
