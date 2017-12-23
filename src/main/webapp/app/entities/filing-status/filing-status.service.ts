import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { FilingStatus } from './filing-status.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FilingStatusService {

    private resourceUrl = SERVER_API_URL + 'api/filing-statuses';

    constructor(private http: Http) { }

    create(filingStatus: FilingStatus): Observable<FilingStatus> {
        const copy = this.convert(filingStatus);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(filingStatus: FilingStatus): Observable<FilingStatus> {
        const copy = this.convert(filingStatus);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<FilingStatus> {
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
     * Convert a returned JSON object to FilingStatus.
     */
    private convertItemFromServer(json: any): FilingStatus {
        const entity: FilingStatus = Object.assign(new FilingStatus(), json);
        return entity;
    }

    /**
     * Convert a FilingStatus to a JSON which can be sent to the server.
     */
    private convert(filingStatus: FilingStatus): FilingStatus {
        const copy: FilingStatus = Object.assign({}, filingStatus);
        return copy;
    }
}
