import { Injectable } from '@angular/core';
import { Http, Response } from '@angular/http';
import { Observable } from 'rxjs/Rx';
import { SERVER_API_URL } from '../../app.constants';

import { JhiDateUtils } from 'ng-jhipster';

import { CompanyLocalTax } from './company-local-tax.model';
import { ResponseWrapper, createRequestOption } from '../../shared';

@Injectable()
export class CompanyLocalTaxService {

    private resourceUrl = SERVER_API_URL + 'api/company-local-taxes';

    constructor(private http: Http, private dateUtils: JhiDateUtils) { }

    create(companyLocalTax: CompanyLocalTax): Observable<CompanyLocalTax> {
        const copy = this.convert(companyLocalTax);
        return this.http.post(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    update(companyLocalTax: CompanyLocalTax): Observable<CompanyLocalTax> {
        const copy = this.convert(companyLocalTax);
        return this.http.put(this.resourceUrl, copy).map((res: Response) => {
            const jsonResponse = res.json();
            return this.convertItemFromServer(jsonResponse);
        });
    }

    find(id: number): Observable<CompanyLocalTax> {
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
     * Convert a returned JSON object to CompanyLocalTax.
     */
    private convertItemFromServer(json: any): CompanyLocalTax {
        const entity: CompanyLocalTax = Object.assign(new CompanyLocalTax(), json);
        entity.createdDate = this.dateUtils
            .convertLocalDateFromServer(json.createdDate);
        return entity;
    }

    /**
     * Convert a CompanyLocalTax to a JSON which can be sent to the server.
     */
    private convert(companyLocalTax: CompanyLocalTax): CompanyLocalTax {
        const copy: CompanyLocalTax = Object.assign({}, companyLocalTax);
        copy.createdDate = this.dateUtils
            .convertLocalDateToServer(companyLocalTax.createdDate);
        return copy;
    }
}
