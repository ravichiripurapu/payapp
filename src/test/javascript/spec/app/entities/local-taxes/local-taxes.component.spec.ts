/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, async } from '@angular/core/testing';
import { Observable } from 'rxjs/Rx';
import { Headers } from '@angular/http';

import { PayappTestModule } from '../../../test.module';
import { LocalTaxesComponent } from '../../../../../../main/webapp/app/entities/local-taxes/local-taxes.component';
import { LocalTaxesService } from '../../../../../../main/webapp/app/entities/local-taxes/local-taxes.service';
import { LocalTaxes } from '../../../../../../main/webapp/app/entities/local-taxes/local-taxes.model';

describe('Component Tests', () => {

    describe('LocalTaxes Management Component', () => {
        let comp: LocalTaxesComponent;
        let fixture: ComponentFixture<LocalTaxesComponent>;
        let service: LocalTaxesService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [PayappTestModule],
                declarations: [LocalTaxesComponent],
                providers: [
                    LocalTaxesService
                ]
            })
            .overrideTemplate(LocalTaxesComponent, '')
            .compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(LocalTaxesComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(LocalTaxesService);
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN
                const headers = new Headers();
                headers.append('link', 'link;link');
                spyOn(service, 'query').and.returnValue(Observable.of({
                    json: [new LocalTaxes(123)],
                    headers
                }));

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(service.query).toHaveBeenCalled();
                expect(comp.localTaxes[0]).toEqual(jasmine.objectContaining({id: 123}));
            });
        });
    });

});
