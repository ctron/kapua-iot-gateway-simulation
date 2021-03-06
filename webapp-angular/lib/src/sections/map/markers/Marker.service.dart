import 'package:webapp_angular/src/data_services/company/Coordinates.dart';
import 'package:webapp_angular/src/data_services/company/Delivery.dart';
import 'package:webapp_angular/src/data_services/company/Transportation.dart';
import 'package:webapp_angular/src/sections/map/icons/Icon.service.dart';
import 'package:angular2/angular2.dart';
import 'package:webapp_angular/src/sections/map/interop/Leaflet.interop.dart';

/// Any available marker for leaflet maps is accessible through this service.
@Injectable()
class MarkerService {

  final IconService _iconsService;

  MarkerService(this._iconsService);

  /// Returns a marker for a couple (delivery, transportation).
  Marker deliveryMarker(Delivery delivery, Transportation transportation) {
    final popupContent =
        "<b>Health state:</b> "+transportation.healthStateString+
        "</br><b>Transportation type:</b> "+transportation.transportationTypeString+
        "</br><b>Coordinates:</b> "+delivery.currentPosition.toString()+
        "</br><b>Status:</b> "+status(delivery.status);

    return Leaflet.marker(
        delivery.currentPosition.latlng,
        new MarkerOptions(icon:  _iconsService.transportation(transportation))
    ).bindPopup(popupContent, null);
  }

  /// Returns a marker for the company's headquarters.
  Marker headquartersMarker(Coordinates headquarters) {
    return Leaflet.marker(
        headquarters.latlng,
        new MarkerOptions(
            icon: _iconsService.headquarters(),
            title: 'Company\'s headquarters',
            zIndexOffset: 9999,
            riseOnHover: true
        )
    ).bindPopup('Company\'s headquarters', null);
  }

  /// Returns the delivery status or "unknown" if it's null
  String status(String deliveryStatus) => (deliveryStatus == null) ? "unknown" : deliveryStatus.toLowerCase();
}
