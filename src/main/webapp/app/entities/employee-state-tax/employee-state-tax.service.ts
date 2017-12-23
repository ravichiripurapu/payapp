import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { EmployeeStateTax } from './employee-state-tax.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class EmployeeStateTaxService {

    private resourceUrl = SERVER_API_URL + 'api/employee-state-taxes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(employeeStateTax: EmployeeStateTax): Observable<EmployeeStateTax> {
        const copy = this.convert(employeeStateTax);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(employeeStateTax: EmployeeStateTax): Observable<EmployeeStateTax> {
        const copy = this.convert(employeeStateTax);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<EmployeeStateTax> {
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
     * Convert a returned JSON object to EmployeeStateTax.
     */
    private convertItemFromServer(json: any): EmployeeStateTax {
        const entity: EmployeeStateTax = Object.assign(new EmployeeStateTax(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a EmployeeStateTax to a JSON which can be sent to the server.
     */
    private convert(employeeStateTax: EmployeeStateTax): EmployeeStateTax {
        const copy: EmployeeStateTax = Object.assign({}, employeeStateTax);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(employeeStateTax.createdDate);
        return copy;
    }
}
