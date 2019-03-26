import { Injectable } from '@angular/core';
import { HttpClient, HttpHeaders } from '@angular/common/http';

@Injectable({
  providedIn: 'root'
})
export class CampaignService {

  uri = '/api/campaign';

  constructor(private http: HttpClient) { }

  getBusinesses() {
    return this
           .http
           .get(`${this.uri}?user_id=admin`);
  }

  updateBusiness(campaign) {
    const headers = new HttpHeaders();
    headers.append('Content-Type', 'application/json');

    return this.http.post(`${this.uri}`, campaign, {headers : headers});
  }


}
