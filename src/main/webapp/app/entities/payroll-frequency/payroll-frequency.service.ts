import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { PayrollFrequency } from './payroll-frequency.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PayrollFrequencyService {

    private resourceUrl = SERVER_API_URL + 'api/payroll-frequencies';

    constructor(private http: Http) { }

    create(payrollFrequency: PayrollFrequency): Observable<PayrollFrequency> {
        const copy = this.convert(payrollFrequency);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(payrollFrequency: PayrollFrequency): Observable<PayrollFrequency> {
        const copy = this.convert(payrollFrequency);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PayrollFrequency> {
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
     * Convert a returned JSON object to PayrollFrequency.
     */
    private convertItemFromServer(json: any): PayrollFrequency {
        const entity: PayrollFrequency = Object.assign(new PayrollFrequency(), json);
        return entity;
    }

    /**
     * Convert a PayrollFrequency to a JSON which can be sent to the server.
     */
    private convert(payrollFrequency: PayrollFrequency): PayrollFrequency {
        const copy: PayrollFrequency = Object.assign({}, payrollFrequency);
        return copy;
    }
}
