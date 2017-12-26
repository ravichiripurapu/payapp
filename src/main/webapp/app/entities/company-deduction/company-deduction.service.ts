import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CompanyDeduction } from './company-deduction.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyDeductionService {

    private resourceUrl = SERVER_API_URL + 'api/company-deductions';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(companyDeduction: CompanyDeduction): Observable<CompanyDeduction> {
        const copy = this.convert(companyDeduction);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(companyDeduction: CompanyDeduction): Observable<CompanyDeduction> {
        const copy = this.convert(companyDeduction);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CompanyDeduction> {
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
     * Convert a returned JSON object to CompanyDeduction.
     */
    private convertItemFromServer(json: any): CompanyDeduction {
        const entity: CompanyDeduction = Object.assign(new CompanyDeduction(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a CompanyDeduction to a JSON which can be sent to the server.
     */
    private convert(companyDeduction: CompanyDeduction): CompanyDeduction {
        const copy: CompanyDeduction = Object.assign({}, companyDeduction);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(companyDeduction.createdDate);
        return copy;
    }
}
