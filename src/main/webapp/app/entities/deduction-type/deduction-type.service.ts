import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { DeductionType } from './deduction-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DeductionTypeService {

    private resourceUrl = SERVER_API_URL + 'api/deduction-types';

    constructor(private http: Http) { }

    create(deductionType: DeductionType): Observable<DeductionType> {
        const copy = this.convert(deductionType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(deductionType: DeductionType): Observable<DeductionType> {
        const copy = this.convert(deductionType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<DeductionType> {
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
     * Convert a returned JSON object to DeductionType.
     */
    private convertItemFromServer(json: any): DeductionType {
        const entity: DeductionType = Object.assign(new DeductionType(), json);
        return entity;
    }

    /**
     * Convert a DeductionType to a JSON which can be sent to the server.
     */
    private convert(deductionType: DeductionType): DeductionType {
        const copy: DeductionType = Object.assign({}, deductionType);
        return copy;
    }
}
