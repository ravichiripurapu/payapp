import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { FutaExemptReasonCode } from './futa-exempt-reason-code.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class FutaExemptReasonCodeService {

    private resourceUrl = SERVER_API_URL + 'api/futa-exempt-reason-codes';

    constructor(private http: Http) { }

    create(futaExemptReasonCode: FutaExemptReasonCode): Observable<FutaExemptReasonCode> {
        const copy = this.convert(futaExemptReasonCode);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(futaExemptReasonCode: FutaExemptReasonCode): Observable<FutaExemptReasonCode> {
        const copy = this.convert(futaExemptReasonCode);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<FutaExemptReasonCode> {
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
     * Convert a returned JSON object to FutaExemptReasonCode.
     */
    private convertItemFromServer(json: any): FutaExemptReasonCode {
        const entity: FutaExemptReasonCode = Object.assign(new FutaExemptReasonCode(), json);
        return entity;
    }

    /**
     * Convert a FutaExemptReasonCode to a JSON which can be sent to the server.
     */
    private convert(futaExemptReasonCode: FutaExemptReasonCode): FutaExemptReasonCode {
        const copy: FutaExemptReasonCode = Object.assign({}, futaExemptReasonCode);
        return copy;
    }
}
