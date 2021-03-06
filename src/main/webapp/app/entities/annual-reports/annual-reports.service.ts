import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { AnnualReports } from './annual-reports.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class AnnualReportsService {

    private resourceUrl = SERVER_API_URL + 'api/annual-reports';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(annualReports: AnnualReports): Observable<AnnualReports> {
        const copy = this.convert(annualReports);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(annualReports: AnnualReports): Observable<AnnualReports> {
        const copy = this.convert(annualReports);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<AnnualReports> {
        return this.http.get(`${this.resourceUrl}/${id}`).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    query(req?: any): Observable<ResponseWrapper> {
        const options = createRequestOption(req);
        return this.http.get(this.resourceUrl, options)
            .map((res: Response) => this.convertResponse(res));
    }

    delete(id: number): Observable<Response> {
        return this.http.delete(`${this.resourceUrl}/${id}`);
    }

    private convertResponse(res: Response): ResponseWrapper {
        const jsonResponse = res.json();
        const result = [];
        for (let i = 0; i < jsonResponse.length; i++) {
            result.push(this.convertItemFromServer(jsonResponse[i]));
        }
        return new ResponseWrapper(res.headers, result, res.status);
    }

    /**
     * Convert a returned JSON object to AnnualReports.
     */
    private convertItemFromServer(json: any): AnnualReports {
        const entity: AnnualReports = Object.assign(new AnnualReports(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a AnnualReports to a JSON which can be sent to the server.
     */
    private convert(annualReports: AnnualReports): AnnualReports {
        const copy: AnnualReports = Object.assign({}, annualReports);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(annualReports.createdDate);
        return copy;
    }
}
