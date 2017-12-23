import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { EmployeeLocalTax } from './employee-local-tax.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EmployeeLocalTaxService {

    private resourceUrl = SERVER_API_URL + 'api/employee-local-taxes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(employeeLocalTax: EmployeeLocalTax): Observable<EmployeeLocalTax> {
        const copy = this.convert(employeeLocalTax);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(employeeLocalTax: EmployeeLocalTax): Observable<EmployeeLocalTax> {
        const copy = this.convert(employeeLocalTax);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<EmployeeLocalTax> {
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
     * Convert a returned JSON object to EmployeeLocalTax.
     */
    private convertItemFromServer(json: any): EmployeeLocalTax {
        const entity: EmployeeLocalTax = Object.assign(new EmployeeLocalTax(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a EmployeeLocalTax to a JSON which can be sent to the server.
     */
    private convert(employeeLocalTax: EmployeeLocalTax): EmployeeLocalTax {
        const copy: EmployeeLocalTax = Object.assign({}, employeeLocalTax);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(employeeLocalTax.createdDate);
        return copy;
    }
}
