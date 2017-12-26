import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { DepositType } from './deposit-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class DepositTypeService {

    private resourceUrl = SERVER_API_URL + 'api/deposit-types';

    constructor(private http: Http) { }

    create(depositType: DepositType): Observable<DepositType> {
        const copy = this.convert(depositType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(depositType: DepositType): Observable<DepositType> {
        const copy = this.convert(depositType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<DepositType> {
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
     * Convert a returned JSON object to DepositType.
     */
    private convertItemFromServer(json: any): DepositType {
        const entity: DepositType = Object.assign(new DepositType(), json);
        return entity;
    }

    /**
     * Convert a DepositType to a JSON which can be sent to the server.
     */
    private convert(depositType: DepositType): DepositType {
        const copy: DepositType = Object.assign({}, depositType);
        return copy;
    }
}
