import { Component, OnInit } from '@angular/core';
import { CampaignService } from './campaign.service';
import { EditCampaignComponent } from './edit-campaign/edit-campaign.component';
import { MatDialog } from '@angular/material';
import {Campaign} from './models';


@Component({
    selector: 'app-root',
    templateUrl: './app.component.html',
    styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

    campaigns: Campaign[];

    constructor(private bs: CampaignService, public dialog: MatDialog) { }

    ngOnInit() {
        this.fetchData();
    }

    fetchData() {
        this.bs
            .getBusinesses()
            .subscribe((data: Campaign[]) => {
                this.campaigns = data;
            });
    }


    openEditDialog(campaign, action): void {
        const dialogRef = this.dialog.open(EditCampaignComponent, {
            width: '500px',
            data: { campaign, action }
        });

        dialogRef.afterClosed().subscribe(result => {
            if (result && result.success) {
                this.fetchData();
            }
        });
    }

}
