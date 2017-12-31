import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PayrollEarnings } from './payroll-earnings.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PayrollEarningsService {

    private resourceUrl = SERVER_API_URL + 'api/payroll-earnings';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(employeePayEarning: PayrollEarnings): Observable<PayrollEarnings> {
        const copy = this.convert(employeePayEarning);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(employeePayEarning: PayrollEarnings): Observable<PayrollEarnings> {
        const copy = this.convert(employeePayEarning);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PayrollEarnings> {
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
     * Convert a returned JSON object to PayrollEarnings.
     */
    private convertItemFromServer(json: any): PayrollEarnings {
        const entity: PayrollEarnings = Object.assign(new PayrollEarnings(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a PayrollEarnings to a JSON which can be sent to the server.
     */
    private convert(employeePayEarning: PayrollEarnings): PayrollEarnings {
        const copy: PayrollEarnings = Object.assign({}, employeePayEarning);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(employeePayEarning.createdDate);
        return copy;
    }
}
