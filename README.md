# eimt-ddd
EiMT lab project

### docker-compose
docker-compose.yml can be used for starting postgresdb service with three databases for the modules.

### Endpoints

`GET /api/slots`
* Get slots from parking-manager

`GET /api/cards`
* Get parking cards from parking-cis

`GET /api/ramps`
* Get ramps from parking-control

`POST /api/slots/reserve`

**Request body**
```
{
	"parking_slot_id": "<parking-slot-id>", 
	"parking_card_id": "<parking-card-id>"
}
```
**Response**
```
<parking-slip-id>
```
* Reserve a parking `parking_slot_id` by parking card `parking_card_id` 
* Returns the parking slip ID as a string - for brevity
* Triggers `ParkingSlipCreated` event which is accepted by `parking-cis` to update `ParkingCard.CardCredit`
* Can trigger `ParkingLotFull` event which is accepted by `parking-control` to update `Ramp.RampStatus`
* Can trigger `ParkingLotAvailable` event which is accepted by `parking-control` to update `Ramp.RampStatus`

`GET /api/slots/free/:parking-slip-id`
* Free slot reserved by ParkingSlip with id `parking-slip-id`
