import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { W2Reports } from './w-2-reports.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class W2ReportsService {

    private resourceUrl = SERVER_API_URL + 'api/w-2-reports';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(w2Reports: W2Reports): Observable<W2Reports> {
        const copy = this.convert(w2Reports);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(w2Reports: W2Reports): Observable<W2Reports> {
        const copy = this.convert(w2Reports);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<W2Reports> {
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
     * Convert a returned JSON object to W2Reports.
     */
    private convertItemFromServer(json: any): W2Reports {
        const entity: W2Reports = Object.assign(new W2Reports(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a W2Reports to a JSON which can be sent to the server.
     */
    private convert(w2Reports: W2Reports): W2Reports {
        const copy: W2Reports = Object.assign({}, w2Reports);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(w2Reports.createdDate);
        return copy;
    }
}
