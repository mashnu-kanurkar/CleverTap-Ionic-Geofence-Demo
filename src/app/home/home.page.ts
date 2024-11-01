import { Component } from '@angular/core';
import { MyCustomPlugin, GeofencePlugin } from 'my-custom-plugin/src';
@Component({
  selector: 'app-home',
  templateUrl: 'home.page.html',
  styleUrls: ['home.page.scss'],
})
export class HomePage {

  text1: string = "CleverTap Geofence Demo";
  text2: string = "Current location is";
  text3: string = "Welcome to our app.";

  constructor() {}

  setText(newText3: string) {
    this.text3 = newText3;
  }

  async initGeofence(){
    await GeofencePlugin.initGeoFence().then(
      (res:any)=>{
        const a = JSON.stringify(res.isInitiated)
        alert("Return value is " + a)
      
      }

    )
  }

  // Function to handle button click
  async triggerLocation() {
    // Your custom task on button click
    console.log("Button clicked!");
    await GeofencePlugin.triggerLocation().then(
      (res:any)=>{
        const a = JSON.stringify(res.value)
        alert("Return value is " + a)
        this.setText(a)
      }
    )
  }

}
