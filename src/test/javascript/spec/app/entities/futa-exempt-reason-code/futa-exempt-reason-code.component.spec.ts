/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { FutaExemptReasonCodeComponent } from '../../../../../../main/webapp/app/entities/futa-exempt-reason-code/futa-exempt-reason-code.component';
import { FutaExemptReasonCodeService } from '../../../../../../main/webapp/app/entities/futa-exempt-reason-code/futa-exempt-reason-code.service';
import { FutaExemptReasonCode } from '../../../../../../main/webapp/app/entities/futa-exempt-reason-code/futa-exempt-reason-code.model';

describe('Component Tests', () => {

    describe('FutaExemptReasonCode Management Component', () => {
        let comp: FutaExemptReasonCodeComponent;
        let fixture: ComponentFixture<FutaExemptReasonCodeComponent>;
        let service: FutaExemptReasonCodeService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [FutaExemptReasonCodeComponent],
                providers: [
                    FutaExemptReasonCodeService
                ]
            })
            .overrideTemplate(FutaExemptReasonCodeComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(FutaExemptReasonCodeComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(FutaExemptReasonCodeService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new FutaExemptReasonCode(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.futaExemptReasonCodes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
