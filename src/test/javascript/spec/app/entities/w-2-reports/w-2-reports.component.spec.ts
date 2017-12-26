/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { W2ReportsComponent } from '../../../../../../main/webapp/app/entities/w-2-reports/w-2-reports.component';
import { W2ReportsService } from '../../../../../../main/webapp/app/entities/w-2-reports/w-2-reports.service';
import { W2Reports } from '../../../../../../main/webapp/app/entities/w-2-reports/w-2-reports.model';

describe('Component Tests', () => {

    describe('W2Reports Management Component', () => {
        let comp: W2ReportsComponent;
        let fixture: ComponentFixture<W2ReportsComponent>;
        let service: W2ReportsService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [W2ReportsComponent],
                providers: [
                    W2ReportsService
                ]
            })
            .overrideTemplate(W2ReportsComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(W2ReportsComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(W2ReportsService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new W2Reports(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.w2Reports[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
