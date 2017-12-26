import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { QuarterlyReports } from './quarterly-reports.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class QuarterlyReportsService {

    private resourceUrl = SERVER_API_URL + 'api/quarterly-reports';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(quarterlyReports: QuarterlyReports): Observable<QuarterlyReports> {
        const copy = this.convert(quarterlyReports);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(quarterlyReports: QuarterlyReports): Observable<QuarterlyReports> {
        const copy = this.convert(quarterlyReports);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<QuarterlyReports> {
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
     * Convert a returned JSON object to QuarterlyReports.
     */
    private convertItemFromServer(json: any): QuarterlyReports {
        const entity: QuarterlyReports = Object.assign(new QuarterlyReports(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a QuarterlyReports to a JSON which can be sent to the server.
     */
    private convert(quarterlyReports: QuarterlyReports): QuarterlyReports {
        const copy: QuarterlyReports = Object.assign({}, quarterlyReports);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(quarterlyReports.createdDate);
        return copy;
    }
}
