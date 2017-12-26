import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PayrollSummary } from './payroll-summary.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PayrollSummaryService {

    private resourceUrl = SERVER_API_URL + 'api/payroll-summaries';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(payrollSummary: PayrollSummary): Observable<PayrollSummary> {
        const copy = this.convert(payrollSummary);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(payrollSummary: PayrollSummary): Observable<PayrollSummary> {
        const copy = this.convert(payrollSummary);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PayrollSummary> {
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
     * Convert a returned JSON object to PayrollSummary.
     */
    private convertItemFromServer(json: any): PayrollSummary {
        const entity: PayrollSummary = Object.assign(new PayrollSummary(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a PayrollSummary to a JSON which can be sent to the server.
     */
    private convert(payrollSummary: PayrollSummary): PayrollSummary {
        const copy: PayrollSummary = Object.assign({}, payrollSummary);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(payrollSummary.createdDate);
        return copy;
    }
}
