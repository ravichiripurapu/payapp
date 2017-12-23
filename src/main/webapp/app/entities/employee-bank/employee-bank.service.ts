import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { EmployeeBank } from './employee-bank.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EmployeeBankService {

    private resourceUrl = SERVER_API_URL + 'api/employee-banks';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(employeeBank: EmployeeBank): Observable<EmployeeBank> {
        const copy = this.convert(employeeBank);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(employeeBank: EmployeeBank): Observable<EmployeeBank> {
        const copy = this.convert(employeeBank);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<EmployeeBank> {
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
     * Convert a returned JSON object to EmployeeBank.
     */
    private convertItemFromServer(json: any): EmployeeBank {
        const entity: EmployeeBank = Object.assign(new EmployeeBank(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a EmployeeBank to a JSON which can be sent to the server.
     */
    private convert(employeeBank: EmployeeBank): EmployeeBank {
        const copy: EmployeeBank = Object.assign({}, employeeBank);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(employeeBank.createdDate);
        return copy;
    }
}
