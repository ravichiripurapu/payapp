import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { BankAccountType } from './bank-account-type.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class BankAccountTypeService {

    private resourceUrl = SERVER_API_URL + 'api/bank-account-types';

    constructor(private http: Http) { }

    create(bankAccountType: BankAccountType): Observable<BankAccountType> {
        const copy = this.convert(bankAccountType);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(bankAccountType: BankAccountType): Observable<BankAccountType> {
        const copy = this.convert(bankAccountType);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<BankAccountType> {
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
     * Convert a returned JSON object to BankAccountType.
     */
    private convertItemFromServer(json: any): BankAccountType {
        const entity: BankAccountType = Object.assign(new BankAccountType(), json);
        return entity;
    }

    /**
     * Convert a BankAccountType to a JSON which can be sent to the server.
     */
    private convert(bankAccountType: BankAccountType): BankAccountType {
        const copy: BankAccountType = Object.assign({}, bankAccountType);
        return copy;
    }
}
