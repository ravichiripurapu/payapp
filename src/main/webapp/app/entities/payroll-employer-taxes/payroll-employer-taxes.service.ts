import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PayrollEmployerTaxes } from './payroll-employer-taxes.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PayrollEmployerTaxesService {

    private resourceUrl = SERVER_API_URL + 'api/payroll-employer-taxes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(payrollEmployerTaxes: PayrollEmployerTaxes): Observable<PayrollEmployerTaxes> {
        const copy = this.convert(payrollEmployerTaxes);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(payrollEmployerTaxes: PayrollEmployerTaxes): Observable<PayrollEmployerTaxes> {
        const copy = this.convert(payrollEmployerTaxes);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PayrollEmployerTaxes> {
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
     * Convert a returned JSON object to PayrollEmployerTaxes.
     */
    private convertItemFromServer(json: any): PayrollEmployerTaxes {
        const entity: PayrollEmployerTaxes = Object.assign(new PayrollEmployerTaxes(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a PayrollEmployerTaxes to a JSON which can be sent to the server.
     */
    private convert(payrollEmployerTaxes: PayrollEmployerTaxes): PayrollEmployerTaxes {
        const copy: PayrollEmployerTaxes = Object.assign({}, payrollEmployerTaxes);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(payrollEmployerTaxes.createdDate);
        return copy;
    }
}
