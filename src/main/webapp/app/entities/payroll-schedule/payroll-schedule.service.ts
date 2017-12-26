import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { PayrollSchedule } from './payroll-schedule.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class PayrollScheduleService {

    private resourceUrl = SERVER_API_URL + 'api/payroll-schedules';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(payrollSchedule: PayrollSchedule): Observable<PayrollSchedule> {
        const copy = this.convert(payrollSchedule);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(payrollSchedule: PayrollSchedule): Observable<PayrollSchedule> {
        const copy = this.convert(payrollSchedule);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<PayrollSchedule> {
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
     * Convert a returned JSON object to PayrollSchedule.
     */
    private convertItemFromServer(json: any): PayrollSchedule {
        const entity: PayrollSchedule = Object.assign(new PayrollSchedule(), json);
        entity.checkDate = this.dateUtils
            .convertLocalDateFromServer(json.checkDate);
        entity.periodEnd = this.dateUtils
            .convertLocalDateFromServer(json.periodEnd);
        entity.periodStart = this.dateUtils
            .convertLocalDateFromServer(json.periodStart);
        entity.approveDate = this.dateUtils
            .convertLocalDateFromServer(json.approveDate);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a PayrollSchedule to a JSON which can be sent to the server.
     */
    private convert(payrollSchedule: PayrollSchedule): PayrollSchedule {
        const copy: PayrollSchedule = Object.assign({}, payrollSchedule);
        copy.checkDate = this.dateUtils
            .convertLocalDateToServer(payrollSchedule.checkDate);
        copy.periodEnd = this.dateUtils
            .convertLocalDateToServer(payrollSchedule.periodEnd);
        copy.periodStart = this.dateUtils
            .convertLocalDateToServer(payrollSchedule.periodStart);
        copy.approveDate = this.dateUtils
            .convertLocalDateToServer(payrollSchedule.approveDate);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(payrollSchedule.createdDate);
        return copy;
    }
}
